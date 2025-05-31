// Funcionalidad para deshabilitar botones de reserva cuando no hay disponibilidad
document.addEventListener('DOMContentLoaded', function() {
    const reservarButtons = document.querySelectorAll('.btn.disabled');
    reservarButtons.forEach(button => {
        button.addEventListener('click', function(e) {
            e.preventDefault();
            alert('Esta habitación no está disponible actualmente.');
        });
    });
    
    // Si viene de una habitación específica, cargar datos
    const urlParams = new URLSearchParams(window.location.search);
    const habitacionId = urlParams.get('habitacion');
    
    if (habitacionId) {
        // Aquí podrías hacer una petición AJAX para cargar los datos de la habitación
        // y prellenar el formulario de reserva
        console.log('Cargando datos para habitación:', habitacionId);
    }
});

function error(mensaje){
    let mensajeError = document.getElementById('mensajeError').value;
    mensajeError.innerHTML = mensaje;
}
// Menú hamburguesa para móviles
document.addEventListener('DOMContentLoaded', function() {
    const burger = document.querySelector('.burger');
    const navLinks = document.querySelector('.nav-links');

    burger.addEventListener('click', function() {
        navLinks.classList.toggle('active');
    });

    // Smooth scrolling para enlaces internos
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function(e) {
            e.preventDefault();
            document.querySelector(this.getAttribute('href')).scrollIntoView({
                behavior: 'smooth'
            });
        });
    });

    // Animación de scroll para navbar
    window.addEventListener('scroll', function() {
        const navbar = document.querySelector('.navbar');
        if (window.scrollY > 50) {
            navbar.style.background = 'rgba(106, 13, 173, 0.95)';
            navbar.style.padding = '0.5rem 5%';
        } else {
            navbar.style.background = 'var(--primary-color)';
            navbar.style.padding = '1rem 5%';
        }
    });
});