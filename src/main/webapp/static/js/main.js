$(document).ready(function(){
    var x = 1;
    var y = 1;
    var currentLocation = window.location;
    $("[data-toggle=tooltip]").tooltip();
    $(".detail-car").hide();
    $("#choicecar").change(function(){
    	$(".detail-car").slideUp("slow");
    	x = 1;
    });
    $(".icon-show-deital").click(function(){
    	var carID = $("#choicecar").val();
    	alert(carID);
    	var detaildiverID = "#detail-car-" + carID;
    	alert(detaildiverID);
        if(x == 1){
            $(detaildiverID).slideDown("slow");
            x = 0;
        }else{
        	$(".detail-car").slideUp("slow");
        	x = 1;
        }
    });
    $(".date-picker").datepicker();
    //"update",setcurrentday()
    //
    $(".date-picker").each(function(){
    	if($(this).val() == "" || $(this).val() == null){
    		$(this).datepicker("update",setcurrentday());
    	}
    });
    $(".date-picker2").datepicker();
    if($("#select-time-use").val() == "inday"){
            $("#in-day").show();
            $("#many-day").hide();
        }
        else{
            $("#in-day").hide();
            $("#many-day").show();
        }
    $("#select-time-use").change(function(){
        if($(this).val() == "inday"){
            $("#in-day").show();
            $("#many-day").hide();
        }
        else{
            $("#in-day").hide();
            $("#many-day").show();
        }
    });
    $("#qwe").click(function(){
       alert($(this).val());
    });
    
    //Show hide info driver
    $(".driver-info").hide();
    $('.show-deital-driver').click(function(){
        $(".driver-info").hide();
        var row_index = $(this).closest("tr").index();
        alert(row_index);
        var idinfo = "#driver-info-"+row_index;
        alert(idinfo);
        $(idinfo).show();
    });
    $("#cancel-filter").hide();
    if(checkfilterornot()){
        $("#cancel-filter").show();
    }
});
function checkfilterornot(currentLocation){
    if(currentLocation.indexOf("filter-stt=") != -1 || currentLocation.indexOf("filter-type=") != -1 || currentLocation.indexOf("filter-date-create=") != -1){
        return true;
    }
    return false;
}
function setcurrentday(){
    var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth()+1; //January is 0!
    var yyyy = today.getFullYear();

    if(dd<10) {
        dd = '0'+dd
    } 

    if(mm<10) {
        mm = '0'+mm
    } 
    return mm + "-" + dd + "-" + yyyy;
}