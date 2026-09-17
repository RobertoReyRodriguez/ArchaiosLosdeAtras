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
    */
}
