package excavation;

public class Excavation {
    /* metodos que generan y guardan la rejilla cada vez que se inicia una nueva excavación con sus tesoros que haya escondidos
    y el estado.

    Debe primero seleccionar region=>arqueologo(de la lista, indicando nombre y nivel)=>lista de tamaños de terrenos=>proceso de excavacion.
    */
    
    
    /*generarTesoros()Los tesoros se colocan a razón de un 10% del número de posiciones de excavación
    redondeando hacia arriba y como mínimo 1. Los mínimos de cada terreno van incluidos en
    esta cifra.

    Probabilidades de cada rareza son: 60% común, 30% infrecuente y 10% raro.
    Todas estas cifras pueden ser modificadas por las habilidades del arqueólogo, añadiendo probabilidades de rarezas,añadiendo tesoros, etc. Pero no puede ser más de un 50% de casillas de tesoro.

    Orden de generación siempre igual, primero mínimos, independientemente de establecidos o aleatorios, despues extras de arqueólogo primero raros, infrecuentes, comunes y aleatorios, si no superan el máximo(terrenos pequeños no pueden tener tesoros raros ni con habilidades).

    En una misma excavación pueden aparecer varios tesoros iguales, sean partes distintas o no.
    */

    /* metodos rejilla(zona de excavacion)
    La rejilla sera referenciada con unos números en el eje vertical y unas letras en el horizontal.
    Por ejemplo:
      a b c
    1 . . .
    2 . . .
    3 . . .

    "." si no esta excavado
    "x" si excavado
    "C" tesoro comun
    "I" tesoro infrecuente
    "R" tesoro raro

    Ciertas habilidades añaden caracteres adicionales, ejemplo:"?"=pista

    La coordenada superior se duplicara si se terminan las letras, es decir, será por ejemplo:AA.
    La cuadrícula debe quedar encuadrada, con espacios necesarios y centrada con las letras superiores.
    
    metodo recursivo para generar letras

    metodo excavar(posicion) intercambia la posicion con el valor de la lista de tesoros
    Al excavar el usuario tiene un numero de intentos determinado del 25% hasta un 75% del tamaño de la rejilla.
    La formula:(25%+extra arqueologo)*factor de aumento de habilidades multiplicado entre sí.
    En cada turno se mostrara las acciones restantes, el estado de la rejilla y se pedirá la posición/es a excavar al usuario indicando numeros y letras.
    Termina cuando no haya mas tesoros o acciones y lo notificará con mensaje.

    finalizar() mostrara el estado de la rejilla y una lista con lo descubierto para ser registrado en Stats y enviados al museo.
    se llama al metodo que recoja los datos y mostrara los tesoros encontrados mostrando:  nombre, descripción, código, tipo y rareza(de forma similar al catálogo).
    Despues mostrara el mensaje de Zeraf(el de la libreria) al mandar tesoros; los puntos y la experiencia ganadas.
    Dependiendo del tamaño de la excavacion el arqueologo ganara mas experiencia extra 2, 5 y 10.
    */
}
