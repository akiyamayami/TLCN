$(document).ready(function(){
	
	// validate form proposal
	$.validator.addMethod("greaterThan", function(value, element, params) {
        if (!/Invalid|NaN/.test(new Date(value)))
            return new Date(value) > new Date($(params).val());
        return isNaN(value) && isNaN($(params).val()) 
            || (Number(value) > Number($(params).val())); 
    },'Ngày kết thúc phải lớn hơn ngày bắt đầu');
    $.validator.addMethod("pdfOnly",function(value,element,params){
        var ext = value.split('.').pop();
        if(ext == "pdf")
            return true;
        return false;
    },"Chỉ được chọn file .pdf");
    $.validator.addMethod('filesize', function(value, element, param) {
        // param = size (en bytes) 
        // element = element to validate (<input>)
        // value = value of the element (file name)
        return this.optional(element) || (element.files[0].size <= param) 
    },"File quá lớn xin chọn file < 10MB");
    $("#Proposal").validate({
        rules: {
            name: "required",
            detail: "required",
            usetodate: { 
                required : true,
                greaterThan : "#usefromdate",
            },
            usefromdate: {
                required : true,
            },
            useindate:{
                required : true,
            },
            file :{
                pdfOnly: true,
                filesize : 10240,
            }
        }
    });
    //
    $('#form-create-car').validate({
    	rules: {
    		licenseplate: "required",
    		type : "required",
    		seats : {
    			required: true,
    			digits: true,
    		}
        }
    });
});