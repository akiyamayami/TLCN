$(document).ready(function(){
	// Js cho create form 
    var x = 1;
    var y = 1;
    $(".detail-car").hide();
    $(".icon-show-deital").click(function(){
        if(x == 1){
            $(".detail-car").slideDown("slow");
            x = 0;
        }else{
            $(".detail-car").slideUp("slow");
            x = 1;
        }
    });
    $(".date-picker").datepicker("update",setcurrentday());
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
    //
});

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