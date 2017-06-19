/////////////////////////////////////     GLOBAL    /////////////////////////////////////////
var $currentPage;

$(function () {
    var $body = $('body');
    if ($('.main-navigation'))
        $mainNavigation.init();
    if ($('.main-footer'))
        $mainFooter.init();
    if ($body.hasClass('login-page'))
        $currentPage = new LoginPage();
    else if ($body.hasClass('recovery-page'))
        $currentPage = new RecoveryPage();
    else if ($body.hasClass('registration-page'))
        $currentPage = new RegistrationPage();
    else if ($body.hasClass('project-page'))
        $currentPage = new ProjectPage();
    else if ($body.hasClass('settings-page'))
        $currentPage = new SettingsPage();
});

function getQueryParameter(parameterName) {
    var queryString = window.top.location.search.substring(1);
    parameterName += "=";
    if (queryString.length > 0) {
        var begin = queryString.indexOf(parameterName);
        if (begin !== -1) {
            begin += parameterName.length;
            var nextParamPos = queryString.indexOf("&", begin);
            var end = nextParamPos === -1 ? queryString.length : nextParamPos;
            return unescape(queryString.substring(begin, end));
        }
    }
    return "null";
}

var extended$ = (function ($, sr) {
    var debounce = function (func, threshold, execAsap) {
        var timeout;
        return function debounced() {
            var obj = this;
            var args = arguments;

            function delayed() {
                if (!execAsap)
                    func.apply(obj, args);
                timeout = null;
            };
            if (timeout)
                clearTimeout(timeout);
            else if (execAsap)
                func.apply(obj, args);
            timeout = setTimeout(delayed, threshold || 100);
        };
    };
    var obj = {};
    obj[sr] = function (fn) {
        return fn ? this.bind('resize', debounce(fn)) : this.trigger(sr);
    };
    $.fn.extend(obj);
    return $;
})(jQuery, 'smartresize');

///////////////////////////////////// COMMON HEADER /////////////////////////////////////////
var $mainNavigation = (function () {
    return {
        init: function () {
            (function initLogout() {
                $('#logout-trigger').click(function (e) {
                    e.preventDefault();
                    $('#logout-form').submit();
                });
            })();
            (function initActiveMenuItem() {
                var pathname = window.location.pathname;
                $('.nav > li > a[href="' + pathname + '"]').parent().addClass('active');
            })();
            (function initSearch() {
                $('#search-form').find('input').keypress(function(event) {
                    if (event.which === 13) {
                        event.preventDefault();
                        $("form").submit();
                    }
                });
            })();
        }
    }
})();

/////////////////////////////////////     FOOTER     /////////////////////////////////////////
var $mainFooter = (function () {
    return {
        init: function () {
            (function initLocaleChange() {
                $('#locale-dropup').find('li a').click(function (e) {
                    e.preventDefault();
                    var url = window.location.href;
                    var langParam = 'lang';
                    var sign = (url.indexOf('?') > -1) ? '&' : '?';
                    var paramValue = getQueryParameter(langParam);
                    if (paramValue === "null")
                        url += sign + langParam + '=' + $(this).text();
                    else {
                        url = url.replace(paramValue, $(this).text());
                    }
                    window.location.href = url;
                });
            })();
            (function initThemeChange() {
                $('#theme-dropup').find('li a').click(function (e) {
                    e.preventDefault();
                    var $body = $('body');
                    $body.removeClass(function (index, className) {
                        return (className.match(/(^|\s)theme-\S+/g) || []).join(' ');
                    });
                    var theme = $(this).text().toLowerCase();
                    $body.addClass('theme-' + theme);
                    $.ajax({
                        url: '/index?theme=' + theme,
                        beforeSend: function (xhr) {
                            xhr.getResponseHeader('Set-Cookie');
                        }
                    });
                });
            })();
        }
    }
})();

/////////////////////////////////////   TAG CLOUD   /////////////////////////////////////////
var $tagCloud = (function () {
    var $element;
    var $tags;
    var tagCloudHeight = '80';
    var footerHeight = $('.main-footer').height();
    var $refresh = function () {
        if (ResponsiveBootstrapToolkit.is('<md')) {
            $('.main-footer').height(footerHeight + +tagCloudHeight);
        } else {
            $('.main-footer').height(footerHeight < tagCloudHeight ? tagCloudHeight : footerHeight);
        }
        $element.width('100%');
        $element.empty();
        $element.jQCloud($tags);
    }
    return {
        init: function (id, tags) {
            $tags = tags;
            $element = $(id);
            if ($element && $tags.length) {
                $element.height(tagCloudHeight);
                $refresh();
                extended$(window).smartresize(function (e) {
                    $refresh();
                }, 50);
            }
        },
        setTags: function (tags) {
            $tags = tags;
            $refresh();
        }
    }
})();

/////////////////////////////////////  VALIDATORS  //////////////////////////////////////////
var $defaultSubmitting = {
    submitHandler: function (form) {
        $(form).find(':submit').button('loading');
        return true;
    }
};
var $ajaxSubmitting = function (successHandler, errorHandler, beforeSubmit) {
    return {
        submitHandler: function (form) {
            var $form = $(form);
            var $btn = $form.find(':submit');
            $btn.button('loading');
            $(form).ajaxSubmit({
                clearForm: true,
                beforeSubmit: function ($form, formData) {
                    if (beforeSubmit) beforeSubmit(formData);
                    console.log('About to submit: \n\n' + $.param(formData));
                    return true;
                },
                success: function (responseText, statusText, xhr, $form) {
                    if (successHandler) successHandler($form, responseText, statusText, xhr, $form);
                    changeMessage($('#modal-message-block'), $('#modal-message-icon'), $('#modal-message-text'), "success", "glyphicon-ok", responseText);
                    $btn.button('reset');
                },
                error: function (error) {
                    if (errorHandler) errorHandler($form, error);
                    changeMessage($('#modal-message-block'), $('#modal-message-icon'), $('#modal-message-text'), "error", "glyphicon-remove", error['responseText']);
                    $btn.button('reset');
                }
                // timeout: 30000
            });
            return false;
        }
    };
}
var $tooltopValidation = {
    errorPlacement: function (error, element) {
        var text = $(error).text();
        if (text && ($(element).tooltipster('content') !== text || !$(element).tooltipster('status').open)) {
            $(element).tooltipster('content', text);
            $(element).tooltipster('open');
        }
    },
    success: function (label, element) {
        $(element).tooltipster('close');
    }
};
var $highlightValidation = {
    onfocusout: false,
    onclick: false,
    validClass: "has-feedback has-success",
    errorClass: "has-feedback has-error text-danger",
    highlight: function (element, errorClass, validClass) {
        $(element).closest('.form-group').addClass(errorClass).removeClass(validClass);
    },
    unhighlight: function (element, errorClass, validClass) {
        if ($(element).prop('required') || $(element).closest('.form-group').hasClass(errorClass))
            $(element).closest('.form-group').removeClass(errorClass).addClass(validClass);
    }
};
var $suppressMessaging = {
    errorPlacement: function (error, element) {},
    success: function (label, element) {}
};
var $validateOnSubmitOnly = {
    onkeyup: false,
    onfocusout: false,
    onclick: false
};

$.validator.addMethod('filesize', function (value, element, param) {
    if (this.optional(element) && element.files.length === 0)
        return true;
    if ($(element).is('[accept]')) {
        var regex = '(.*' + $(element).attr('accept').split(',').join(')|(.*\\') + ')';
    } else var regex = ".*";
    return (element.files[0].size <= param) && value.match(regex);
}, "File is too large");
$.validator.addMethod("regex", function (value, element, regexp) {
    var re = new RegExp(regexp);
    return this.optional(element) || re.test(value);
}, "Please check your input.");
$.validator.addMethod("notEqual", function (value, element, param) {
    return this.optional(element) || value !== $(param).val();
}, "This has to be different.");

function changeMessage($divTag, $iconTag, $textTag, $divClass, $iconClass, $messageText) {
    var $messageAnimateTime = 150;
    var $messageShowTime = 2500;

    var fadeMessage = function ($messageId, $messageText) {
        $messageId.fadeOut($messageAnimateTime, function () {
            $(this).val($messageText).fadeIn($messageAnimateTime);
        });
    };
    var $messageOld = $textTag.val();
    fadeMessage($textTag, $messageText);
    $divTag.addClass($divClass);
    $iconTag.removeClass("glyphicon-chevron-right").addClass($iconClass).addClass($divClass);
    setTimeout(function () {
        fadeMessage($textTag, $messageOld);
        $divTag.removeClass($divClass);
        $iconTag.addClass("glyphicon-chevron-right").removeClass($iconClass).removeClass($divClass);
    }, $messageShowTime);
}

function FormContainer(modal, contentDiv) {
    var $this = this;
    var $contentDiv = contentDiv;
    var $modal = modal;
    var $forms = [];
    var $animateModalTime = 300;

    var $initializeModal = (function () {
        $modal.on('hide.bs.modal', function (e) {
            $this.$forms.forEach(function (e) {
                e.reset();
            })
        });
        $modal.on('shown.bs.modal', function (e) {
            if ($this.$currentForm !== $this.$primaryForm)
                $this.changeForm($this.$primaryForm);
        });
    });

    var $animateModal = (function ($oldForm, $newForm) {
        var $oldH = $oldForm.height();
        var $newH = $newForm.height();
        $contentDiv.css("height", $oldH);
        $oldForm.fadeToggle($animateModalTime, function () {
            $contentDiv.animate({
                height: $newH
            }, $animateModalTime, function () {
                $newForm.fadeToggle($animateModalTime);
            });
        });
    });

    return {
        changeForm: function ($newForm) {
            this.$currentForm.closeTooltips();
            $animateModal(this.$currentForm['$form'], $newForm['$form']);
            $('#modal-message-text').val($newForm['$form'].attr('data-default-message'));
            this.$currentForm = $newForm;
        },
        setForms: function (forms) {
            $forms = forms;
        },
        $currentForm: null,
        $primaryForm: null
    };
}

function Form(form, validator) {
    var $form = form;
    var $validator = $form.validate(validator);

    return {
        init: function () {
            $form.find('input').tooltipster({
                trigger: 'custom',
                onlyOne: true,
                position: 'top'
            });

            $form.find('.form-error-container span[id$=".errors"]').each(function (i, e) {
                var name = $(e).attr('id').slice(0, -7);
                var $input = $form.find('input[name="' + name + '"]');
                if (name) {
                    var text = $(e).text();
                    if ($input.length > 0 && $($input).is(':visible')) {
                        $input.closest('.form-group').addClass('has-error');
                        $input.tooltipster('content', text);
                        $input.tooltipster('open');
                    } else {
                        changeMessage($('#modal-message-block'), $('#modal-message-icon'), $('#modal-message-text'), "error", "glyphicon-remove", text);
                    }
                }
            });
        },
        reset: function () {
            this.closeTooltips();
            $validator.resetForm();
            $form.find('input:text, input:password, input:file, select, textarea').filter(function (i, e) {
                return !$(e).prop('readonly');
            }).val('');
            $form.find('input:radio, input:checkbox').removeAttr('checked');
            $form.find('.form-group').removeClass('has-success');
        },
        closeTooltips: function () {
            $form.find('input.tooltipstered').tooltipster('close');
        },
        $form: $form
    };
}

///////////////////////////////////// REGISTRATION //////////////////////////////////////////
function RegistrationPage() {
    var $registerFormValidator = Object.assign({
        rules: {
            firstName: {
                required: true,
                minlength: 2,
                maxlength: 30
            },
            lastName: {
                required: true,
                minlength: 2,
                maxlength: 30
            },
            middleName: {
                minlength: 2,
                maxlength: 30
            },
            gender: {
                required: true
            },
            email: {
                required: true,
                email: true
            },
            username: {
                required: true,
                minlength: 2,
                maxlength: 30
            },
            password: {
                required: true,
                minlength: 8,
                maxlength: 25,
                regex: "^(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$",
                notEqual: "#register-username"
            },
            matchingPassword: {
                required: true,
                equalTo: "#register-password"
            },
            profileImage: {
                filesize: 2097152
                //            accept: "png|jpe?g|gif"
            }
        },
        messages: {
            profileImage: "File must be JPG(JPEG), GIF or PNG, less than 2MB",
            password: {
                regex: "Should contain at least one uppercase letter and one digit",
                notEqual: "Password has to be diffrent to username."
            }
        }
    }, $defaultSubmitting, $highlightValidation, $tooltopValidation);
    var $registerForm = new Form($('#register-form'), $registerFormValidator);
    $registerForm.init();
};

/////////////////////////////////////     LOGIN    //////////////////////////////////////////
function LoginPage() {
    var $modal = new FormContainer($('#login-modal'), $('#div-forms'));

    var $loginFormValidator = Object.assign({
        rules: {
            credentials: {
                required: true,
                minlength: 2,
                maxlength: 30
            },
            password: {
                required: true,
                minlength: 8,
                maxlength: 25
            }
        }
    }, $defaultSubmitting, $tooltopValidation, $validateOnSubmitOnly);
    var $loginForm = new Form($('#login-form'), $loginFormValidator);
    $loginForm.init();

    var $lostFormValidator = Object.assign({
        rules: {
            credentials: {
                required: true,
                minlength: 2,
                maxlength: 30
            }
        }
    }, $ajaxSubmitting(), $tooltopValidation, $validateOnSubmitOnly);
    var $lostForm = new Form($('#lost-form'), $lostFormValidator);
    $lostForm.init();

    $modal.setForms([$loginForm, $lostForm]);
    $modal.$primaryForm = $modal.$currentForm = $loginForm;

    $('.to-lost-btn').click(function () {
        $modal.changeForm($lostForm);
    });
    $('.to-login-btn').click(function () {
        $modal.changeForm($loginForm);
    });

    return {
        $formContainer: $modal
    };
};

////////////////////////////////////     RECOVERY    /////////////////////////////////////////
function RecoveryPage() {
    var $recoveryFormValidator = Object.assign({
        rules: {
            password: {
                required: true,
                minlength: 8,
                maxlength: 25,
                regex: "^(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$",
                notEqual: "#register-username"
            },
            matchingPassword: {
                required: true,
                equalTo: "#register-password"
            }
        },
        messages: {
            password: {
                regex: "Should contain at least one uppercase letter and one digit",
                notEqual: "Password has to be diffrent to username."
            }
        }
    }, $defaultSubmitting, $highlightValidation, $tooltopValidation);
    var $recoveryForm = new Form($('#recovery-form'), $recoveryFormValidator);
    $recoveryForm.init();
};

/////////////////////////////////////    PROJECT   //////////////////////////////////////////
function ProjectPage() {
    var simplemde;
    var saveToPdf = function () {
        var pdf = new jsPDF('p', 'pt', 'letter');
        var source = simplemde.options.previewRender(simplemde.value());
        var specialElementHandlers = {
            '#bypassme': function (element, renderer) {
                return true
            }
        };
        var margins = {
            top: 80,
            bottom: 60,
            left: 40,
            width: 522
        };
        pdf.fromHTML(source, margins.left, margins.top, {
                'width': margins.width,
                'elementHandlers': specialElementHandlers
            },

            function (dispose) {
                pdf.save('Test.pdf');
            }, margins);
    };
    $('#to-pdf-btn').click(function () {
        saveToPdf();
    });
    $('#mde').change(function () {
        $.ajax({
            url: '/',
            data: simplemde
        });
    });

    return {
        displayRepo: function (userName, divId, repoName) {
            Github.repoActivity({
                username: userName,
                selector: '#' + divId,
                reponame: repoName
            });
        },
        displayOrganisation: function (orgName, divId) {
            Github.userActivity({
                username: orgName,
                selector: '#' + divId
            });
        },
        saveToPDF: saveToPdf,
        initializeMDE: function () {
            simplemde = new SimpleMDE({
                element: document.getElementById("mde"),
                autosave: {
                    enabled: true,
                    uniqueId: "MyUniqueID",
                    delay: 1000
                },
                blockStyles: {
                    bold: "__",
                    italic: "_"
                }
            });
            simplemde.codemirror.on("change", function () {
                // saves after each action
                // TODO: add saving to db (for other users)
            });
        }
    }
};

/////////////////////////////////////   SETTINGS   //////////////////////////////////////////
function SettingsPage() {
    var $personalSettingsFormValidator = Object.assign({
        rules: {
            firstName: {
                minlength: 2,
                maxlength: 30
            },
            lastName: {
                minlength: 2,
                maxlength: 30
            },
            middleName: {
                minlength: 2,
                maxlength: 30
            },
            gender: {
                required: true
            }
        }
    }, $defaultSubmitting, $highlightValidation, $tooltopValidation);
    var $personalSettingsForm = new Form($('#personal-settings-form'), $personalSettingsFormValidator);
    $personalSettingsForm.init();

    var $accountSettingsFormValidator = Object.assign({
        rules: {
            oldPassword: {
                required: true
            },
            password: {
                required: true,
                minlength: 8,
                maxlength: 25,
                regex: "^(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$"
            },
            matchingPassword: {
                required: true,
                equalTo: "#account-setting-password"
            },
            profileImage: {
                filesize: 2097152
                //            accept: "png|jpe?g|gif"
            }
        },
        messages: {
            password: {
                regex: "Should contain at least one uppercase letter and one digit",
                notEqual: "Password has to be diffrent to username."
            }
        }
    }, $defaultSubmitting, $highlightValidation, $tooltopValidation);
    var $accountSettingsForm = new Form($('#account-settings-form'), $accountSettingsFormValidator);
    $accountSettingsForm.init();

    var spinnerOpts = {
        lines: 11,
        length: 25,
        width: 5,
        radius: 23,
        scale: 1.0,
        corners: 1,
        color: '#000',
        opacity: 0.25,
        rotate: 0,
        direction: 1,
        speed: 1,
        trail: 76,
        fps: 20,
        zIndex: 2e9,
        className: 'spinner',
        top: '30%',
        left: '50%',
        shadow: false,
        hwaccel: false,
        position: 'absolute'
    };
    var spinner;
    var $updatePhotoFormValidator = Object.assign({
        rules: {
            profileImage: {
                filesize: 2097152
                //            accept: "png|jpe?g|gif"
            }
        },
        messages: {
            profileImage: "File must be JPG(JPEG), GIF or PNG, less than 2MB"
        }
    }, $ajaxSubmitting(function ($form, responseText) {
        spinner.stop();
        $('#profile-image').attr("src", responseText);
        $updatePhotoForm.reset();
    }, function ($form, error) {
        spinner.stop();
        console.log(error);
        $updatePhotoForm.reset();
    }, function ($form, formData) {
        spinner = new Spinner(spinnerOpts).spin($('#profile-image-spinner')[0]);
    }), $highlightValidation, $tooltopValidation);
    var $updatePhotoForm = new Form($('#update-photo-form'), $updatePhotoFormValidator);
    $updatePhotoForm.init();
    $updatePhotoForm['$form'].change(function () {
        console.log('changed');
        $updatePhotoForm['$form'].submit();
    });

    $('.settings-list').find('li').click(function (e) {
        $('.settings-list').find('li a').removeClass('active');
        $(this).find('a').addClass('active');
    })
};
