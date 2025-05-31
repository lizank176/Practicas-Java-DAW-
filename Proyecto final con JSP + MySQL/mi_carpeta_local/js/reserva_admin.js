// Función para actualizar el resumen de estados
function actualizarResumen() {
    let confirmadas = 0;
    let pendientes = 0;
    let canceladas = 0;
    
    document.querySelectorAll('.estado').forEach(estado => {
        const textoEstado = estado.textContent.toLowerCase();
        if (textoEstado.includes('confirmada')) confirmadas++;
        else if (textoEstado.includes('pendiente')) pendientes++;
        else if (textoEstado.includes('cancelada')) canceladas++;
    });
    
    document.getElementById('confirmadas').textContent = confirmadas;
    document.getElementById('pendientes').textContent = pendientes;
    document.getElementById('canceladas').textContent = canceladas;
    document.getElementById('total').textContent = confirmadas + pendientes + canceladas;
}

// Llamar a la función al cargar la página
window.onload = actualizarResumen;

// Función para buscar reservas
function buscarReservas() {
    const filtro = document.getElementById('searchInput').value;
    window.location.href = 'index.jsp?do=user/buscarReservas&filtro=' + encodeURIComponent(filtro);
}

// Función para editar reserva
function editarReserva(idReserva) {
    window.location.href = 'index.jsp?do=user/editarReserva&id=' + idReserva;
}

// Función para confirmar eliminación
function confirmarEliminar(idReserva) {
    if (confirm('¿Está seguro que desea eliminar esta reserva?')) {
        window.location.href = 'index.jsp?do=user/eliminarReserva&id=' + idReserva;
    }
}

// Función para exportar a PDF
function exportarPDF(idReserva) {
    const { jsPDF } = window.jspdf;
    const doc = new jsPDF();
    
    // Obtener datos de la fila
    const fila = document.querySelector(`tr[data-id="${idReserva}"]`);
    const datos = Array.from(fila.cells).map(cell => cell.textContent);
    
    // Crear PDF
    doc.text('Detalle de Reserva', 10, 10);
    doc.autoTable({
        startY: 20,
        head: [['Campo', 'Valor']],
        body: [
            ['N° Reserva', datos[0]],
            ['Cliente', datos[1]],
            ['Fecha Reserva', datos[2]],
            ['Fecha Entrada', datos[3]],
            ['Fecha Salida', datos[4]],
            ['Precio Total', datos[5]],
            ['Anticipo', datos[6]],
            ['Estado', datos[7]]
        ]
    });
    
    doc.save(`reserva_${idReserva}.pdf`);
}

// Función para exportar toda la tabla a PDF
function exportarTablaPDF() {
    const { jsPDF } = window.jspdf;
    const doc = new jsPDF();
    
    const tabla = document.getElementById('tablaReservas');
    const headers = Array.from(tabla.querySelectorAll('thead th')).map(th => th.textContent);
    const rows = Array.from(tabla.querySelectorAll('tbody tr')).map(tr => 
        Array.from(tr.cells).map(td => td.textContent)
    );
    
    doc.text('Listado de Reservas', 10, 10);
    doc.autoTable({
        startY: 20,
        head: [headers],
        body: rows
    });
    
    doc.save('reservas_hotel.pdf');
}