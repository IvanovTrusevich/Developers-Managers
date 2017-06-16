var simplemde;

function saveToPDF() {
	var pdf = new jsPDF('p', 'pt', 'letter');
	source = simplemde.options.previewRender(simplemde.value());
	specialElementHandlers = {
		'#bypassme' : function(element, renderer) {
			return true
		}
	};
	margins = {
		top : 80,
		bottom : 60,
		left : 40,
		width : 522
	};
	pdf.fromHTML(source, margins.left, margins.top, {
		'width' : margins.width,
		'elementHandlers' : specialElementHandlers
	},

	function(dispose) {
		pdf.save('Test.pdf');
	}, margins);
}

function initializeMDE() {
	simplemde = new SimpleMDE({
		element : document.getElementById("mde"),
		autosave : {
			enabled : true,
			uniqueId : "MyUniqueID",
			delay : 1000,
		},
		blockStyles : {
			bold : "__",
			italic : "_"
		},
	});
	simplemde.codemirror.on("change", function() {
		// saves after each action
		// TODO: add saving to db (for other users)
	});
}