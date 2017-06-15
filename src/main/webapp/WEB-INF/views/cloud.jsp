<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>cloud</title>


<link rel="stylesheet" type="text/css" href="res/libs/jqcloud/jqcloud.css" />
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.4/jquery.min.js"></script>
<script type="text/javascript" src="res/libs/jqcloud/jqcloud-1.0.4.js"></script>
<script type="text/javascript">
    /*!
     * Create an array of word objects, each representing a word in the cloud
     */
    var word_array = [
        {text: "Lorem", weight: 15},
        {text: "Ipsum", weight: 9, link: "http://jquery.com/"},
        {text: "Dolor", weight: 6, html: {title: "I can haz any html attribute"}},
        {text: "Sit", weight: 7},
        {text: "Amet", weight: 5}
        // ...as many words as you want
    ];
    $(function() {
        // When DOM is ready, select the container element and call the jQCloud method, passing the array of words as the first argument.
        $("#example").jQCloud(word_array);
    });
</script>


<body>
<!-- Element where elFinder will be created (REQUIRED) -->
<div id="example" style="width: 550px; height: 350px;"></div>

</body>
</html>