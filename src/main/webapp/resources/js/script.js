function displayRepo(userName, divId, repoName) {
    Github.repoActivity({
        username: userName,
        selector: '#' + divId,
        reponame: repoName
    });
}

function displayOrganisation(orgName, divId) {
    Github.userActivity({
        username: orgName,
        selector: '#' + divId
    });
}

/////////////////////////////////////    HEADER    //////////////////////////////////////////
$(function () {
    // $('li a');
    // var clauses = document.querySelector(".main-navigation").querySelectorAll("li");
    // for (i = 0; i < clauses.length; i++) {
    //     if (clauses[i].getElementsByTagName("a")[0].getAttribute('href') === "${requestScope['javax.servlet.forward.request_uri']}") {
    //         clauses[i].classList.add("active")
    //     }
    // }
});

///////////////////////////////////// REGISTRATION //////////////////////////////////////////
$(function () {
    function Modal($modal, $contentDiv) {
        var $this = this;
        this.contentDiv = $contentDiv;
        this.modal = $modal;
        this.forms = [];
        this.$currentForm = null;
        this.$primaryForm = null;

        this.modal.on('shown.bs.modal', function (e) {
            if ($this.$currentForm !== $this.$primaryForm)
                $this.changeForm($this.$primaryForm);
        });

        this.modal.on('hide.bs.modal', function (e) {
            $this.forms.forEach(function (e) {
                e.reset();
            })
        });

        var $animateModalTime = 300;
        var $messageAnimateTime = 150;
        var $messageShowTime = 2000;

        var fadeMessage = function ($messageId, $messageText) {
            $messageId.fadeOut($messageAnimateTime, function () {
                $(this).val($messageText).fadeIn($messageAnimateTime);
            });
        };

        var animateModal = function ($oldForm, $newForm) {
            var $oldH = $oldForm.height();
            var $newH = $newForm.height();
            $this.contentDiv.css("height", $oldH);
            $oldForm.fadeToggle($animateModalTime, function () {
                $this.contentDiv.animate({
                    height: $newH
                }, $animateModalTime, function () {
                    $newForm.fadeToggle($animateModalTime);
                });
            });
        };

        this.changeMessage = function ($divTag, $iconTag, $textTag, $divClass, $iconClass, $messageText) {
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

        this.changeForm = function ($newForm) {
            this.$currentForm.closeTooltips();
            animateModal(this.$currentForm['form'], $newForm.form);
            $('#modal-message-text').val($newForm.form.attr('data-default-message'));
            this.$currentForm = $newForm;
        }
    }

    function Form($form, $validator) {
        this.form = $form;
        this.validator = this.form.validate($validator);

        $form.find('input').tooltipster({
            trigger: 'custom',
            onlyOne: true,
            position: 'right'
        });

        this.reset = function () {
            this.closeTooltips();
            this.validator.resetForm();
            this.form.find('input:text, input:password, input:file, select, textarea').filter(function (i, e) {
                return !$(e).prop('readonly');
            }).val('');
            this.form.find('input:radio, input:checkbox').removeAttr('checked');
            this.form.find('.form-group').removeClass('has-success');
        }

        this.closeTooltips = function () {
            this.form.find('input.tooltipstered').tooltipster('close');
        }
    }

    var $modal = new Modal($('#login-modal'), $('#div-forms'));

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
        return this.optional(element) || value != $(param).val();
    }, "This has to be different.");

    var $defaultSubmitting = {
        submitHandler: function (form) {
            var $btn = $(form).find(':submit');
            $btn.button('loading');
            form.ajaxForm({
                beforeSubmit: function (xhr) {
                    xhr.setRequestHeader("X-Ajax-call", "true");
                },
                success: function(data) {
                    $modal.changeMessage($('#modal-message-block'), $('#modal-message-icon'), $('#modal-message-text'), "success", "glyphicon-ok", "OK");
                    $btn.button('reset');
                    $modal.modal('hide');
                    $('#login-popup').css('display', 'none');
                    $('#user-dropdown').css('display', 'block');
                    $('#user-dropdown-username').text(data);
                },
                error: function(data) {
                    changeMessage($('#div-register-msg'), $('#icon-register-msg'), $('#text-register-msg'), "error", "glyphicon-remove", data);
                }
            });
            return false;
        }
    };
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
    var $validateOnSubmit = {
        onkeyup: false,
        onfocusout: false,
        onclick: false
    };

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
    }, $defaultSubmitting, $highlightValidation, $tooltopValidation, $validateOnSubmit);
    var $loginForm = new Form($('#login-form'), $loginFormValidator);

    var $lostFormValidator = Object.assign({
        rules: {
            credentials: {
                required: true,
                minlength: 2,
                maxlength: 30
            }
        }
    }, $defaultSubmitting, $highlightValidation, $tooltopValidation, $validateOnSubmit);
    var $lostForm = new Form($('#lost-form'), $lostFormValidator);

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

    $modal.forms = [$loginForm, $lostForm, $registerForm];
    $modal.$primaryForm = $modal.$currentForm = $loginForm;

    $('.to-register-btn').click(function () {
        $modal.changeForm($registerForm);
    });
    $('.to-login-btn').click(function () {
        $modal.changeForm($loginForm);
    });
    $('.to-lost-btn').click(function () {
        $modal.changeForm($lostForm);
    });
});
