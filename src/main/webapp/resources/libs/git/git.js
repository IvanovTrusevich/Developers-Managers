
function displayRepo(userName, divId, repoName){
	Github.repoActivity({
			username: userName,
        	selector: '#' + divId,
        	reponame: repoName
		});
}

function displayOrganisation(orgName, divId){
	Github.userActivity({
			username: orgName,
        	selector: '#' + divId	
		});
}

webix.ready(function(){
    //object constructor
    webix.ui({
        view:"filemanager",
        id:"files"
    });
    // loading data source
    $$("files").load("data/files.php");
});