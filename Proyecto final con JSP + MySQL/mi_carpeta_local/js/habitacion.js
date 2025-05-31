document.addEventListener('DOMContentLoaded', function() {
    // Cambiar imagen principal al hacer clic en miniaturas
    const thumbnails = document.querySelectorAll('.thumbnail');
    const mainImage = document.querySelector('.main-image img');
    
    thumbnails.forEach(thumb => {
        thumb.addEventListener('click', function() {
            // Remover clase active de todas las miniaturas
            thumbnails.forEach(t => t.classList.remove('active'));
            // Añadir clase active a la miniatura clickeada
            this.classList.add('active');
            // Cambiar imagen principal
            const newSrc = this.querySelector('img').src;
            mainImage.src = newSrc;
        });
    });
    
    // Calcular precio total al cambiar fechas
    const fechaEntrada = document.getElementById('fechaEntrada');
    const fechaSalida = document.getElementById('fechaSalida');
    const nightsDisplay = document.getElementById('nights');
    const totalPriceDisplay = document.getElementById('total-price');
    // Asegúrate de definir pricePerNight en tu HTML, por ejemplo:
    // <script>window.pricePerNight = <%= habitacion.getPrecioNoche() %>;</script>
    const pricePerNight = window.pricePerNight || 0;
    
    function calcularTotal() {
        if (fechaEntrada.value && fechaSalida.value) {
            const entrada = new Date(fechaEntrada.value);
            const salida = new Date(fechaSalida.value);
            
            // Validar que la fecha de salida sea posterior a la de entrada
            if (salida <= entrada) {
                alert('La fecha de salida debe ser posterior a la de entrada');
                fechaSalida.value = '';
                return;
            }
            
            // Calcular diferencia en días
            const diffTime = salida - entrada;
            const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
            
            // Actualizar display
            nightsDisplay.textContent = diffDays;
            totalPriceDisplay.textContent = (diffDays * pricePerNight) + '€';
        }
    }
    
    fechaEntrada.addEventListener('change', calcularTotal);
    fechaSalida.addEventListener('change', calcularTotal);
    
    // Validar fechas mínimas (hoy)
    const today = new Date().toISOString().split('T')[0];
    fechaEntrada.min = today;
    
    fechaEntrada.addEventListener('change', function() {
        fechaSalida.min = this.value;
    });
    
    // Animación al hacer scroll
    const animateOnScroll = function() {
        const elements = document.querySelectorAll('.details-content, .booking-form-container');
        
        elements.forEach(el => {
            const elementPosition = el.getBoundingClientRect().top;
            const screenPosition = window.innerHeight / 1.2;
            
            if (elementPosition < screenPosition) {
                el.style.opacity = '1';
                el.style.transform = 'translateY(0)';
            }
        });
    };
    
    // Establecer estilos iniciales para la animación
    document.querySelector('.details-content').style.opacity = '0';
    document.querySelector('.details-content').style.transform = 'translateY(20px)';
    document.querySelector('.details-content').style.transition = 'all 0.6s ease';
    
    document.querySelector('.booking-form-container').style.opacity = '0';
    document.querySelector('.booking-form-container').style.transform = 'translateY(20px)';
    document.querySelector('.booking-form-container').style.transition = 'all 0.6s ease 0.2s';
    
    window.addEventListener('scroll', animateOnScroll);
    animateOnScroll(); // Ejecutar una vez al cargar
    
    // Manejar envío del formulario
    const reservaForm = document.getElementById('reservaForm');
    
    reservaForm.addEventListener('submit', function(e) {
        e.preventDefault();
        
        if (!fechaEntrada.value || !fechaSalida.value) {
            alert('Por favor, seleccione fechas de entrada y salida');
            return;
        }
        
        // Aquí podrías añadir validación adicional o enviar con AJAX
        this.submit();
    });
});