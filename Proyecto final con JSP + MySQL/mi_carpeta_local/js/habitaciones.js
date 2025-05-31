document.addEventListener('DOMContentLoaded', function() {
    // Filtros interactivos
    const filterTags = document.querySelectorAll('.filter-tag');
    const priceSlider = document.querySelector('.slider');
    const capacitySelect = document.querySelector('.capacity-select');
    
    // Manejar clic en tags de filtro
    filterTags.forEach(tag => {
        tag.addEventListener('click', function() {
            filterTags.forEach(t => t.classList.remove('active'));
            this.classList.add('active');
            // Aquí podrías añadir lógica para filtrar las habitaciones
        });
    });
    
    // Actualizar precios al mover el slider
    priceSlider.addEventListener('input', function() {
        // Aquí podrías actualizar el rango de precios mostrado
    });
    
    // Filtrar por capacidad
    capacitySelect.addEventListener('change', function() {
        // Aquí podrías filtrar las habitaciones por capacidad
    });
    
    // Efecto hover en tarjetas de habitación
    const roomCards = document.querySelectorAll('.room-card');
    roomCards.forEach(card => {
        card.addEventListener('mouseenter', function() {
            this.style.transform = 'translateY(-5px)';
            this.style.boxShadow = '0 10px 25px rgba(0, 0, 0, 0.1)';
        });
        
        card.addEventListener('mouseleave', function() {
            this.style.transform = '';
            this.style.boxShadow = '';
        });
    });
    
    // Menú móvil
    const burger = document.querySelector('.burger');
    const navLinks = document.querySelector('.nav-links');
    
    burger.addEventListener('click', function() {
        navLinks.style.display = navLinks.style.display === 'flex' ? 'none' : 'flex';
    });
});
function aplicarFiltros() {
    const selectedTipo = document.querySelector('.filter-tag.active')?.textContent.toLowerCase();
    const selectedCapacidad = capacitySelect.value;
    const maxPrecio = parseInt(priceSlider.value);

    const roomCards = document.querySelectorAll('.room-card');

    roomCards.forEach(card => {
        const tipo = card.dataset.tipo;
        const precio = parseInt(card.dataset.precio);
        const capacidad = parseInt(card.dataset.capacidad);

        const pasaTipo = selectedTipo === 'todas' || tipo.includes(selectedTipo);
        const pasaPrecio = precio <= maxPrecio;
        const pasaCapacidad = selectedCapacidad === 'Cualquiera' || 
                              (selectedCapacidad === '1 persona' && capacidad === 1) ||
                              (selectedCapacidad === '2 personas' && capacidad === 2) ||
                              (selectedCapacidad === '3+ personas' && capacidad >= 3);

        if (pasaTipo && pasaPrecio && pasaCapacidad) {
            card.style.display = 'block';
        } else {
            card.style.display = 'none';
        }
    });
}
