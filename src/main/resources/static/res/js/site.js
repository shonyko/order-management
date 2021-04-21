$.extend( true, $.fn.dataTable.ext.buttons,{
    "btnCopy": {"extend": "copyHtml5", exportOptions: {columns: ':visible'}, "text": "Copiaza", "titleAttr": "Copiaza", "className": "btn btn-primary"},
    "btnExcel": {"extend": "excelHtml5", exportOptions: {columns: ':visible'}, "text": "Excel", "titleAttr": "Excel", "className": "btn btn-primary ml-1"},
    "btnPDF": {"extend": "pdfHtml5", exportOptions: {columns: ':visible'}, "text": "Pdf", "titleAttr": "Pdf", "className": "btn btn-primary ml-1"},
    "btnPrint": {"extend": "print", exportOptions: {columns: ':visible'}, "text": "Imprima", "titleAttr": "Imprima", "className": "btn btn-primary ml-1"}
});