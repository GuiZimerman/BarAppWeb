var selectedRow = null; 
var selectedId = null; 

// Função para a tabela com id 'contasAbertasTbl'
$(document).ready(function () {
    $('#contasAbertasTbl tbody tr').click(function () {
        if (selectedRow) {
            $(selectedRow).removeClass('selected');
        }

        $(this).addClass('selected'); 
        selectedRow = this; 

        selectedId = $(this).find('td')[0].innerText;
        alert("ID selecionado: " + selectedId); 
    });
});

// Função para a tabela com id 'abrirContaTbl'
$(document).ready(function () {
    var selectedRow = null;  
    var selectedId = null;   

    $('#abrirContaTbl tbody tr').click(function () {
        if (selectedRow) {
            $(selectedRow).removeClass('selected'); 
        }

        $(this).addClass('selected');  
        selectedRow = this;           

        
        selectedId = $(this).children('td:first').text();
        alert("ID selecionado: " + selectedId); 
    });
});
