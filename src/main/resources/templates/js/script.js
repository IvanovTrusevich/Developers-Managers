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
