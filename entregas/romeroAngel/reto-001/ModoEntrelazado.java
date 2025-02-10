class Pixel {
    private char valor;

    public Pixel() {
        this.valor = '.';
    }

    public Pixel(char valor){
        this.valor = valor;
    }

    
    public char getValor(){
        return valor;
    }

    public void setValor(char nuevoValor){
        this.valor = nuevoValor;
    }
}

class Frame {
    private Pixel[][] pixeles;

    public Frame(int filas, int columnas){
        this.pixeles = new Pixel[filas][columnas];

        for(int i = 0;i<filas;i++){
            for (int j = 0;j<columnas;j++){
                pixeles[i][j] = new Pixel();
            }
        }
    }

    public void actualizarPixel(int fila, int columna, char nuevoValor){
        if (estaEnRango(fila, columna)) {
            pixeles[fila][columna].setValor(nuevoValor);
        } else {
            System.out.println("Error: dimensiones fuera de rango.");
        }

    }

    private boolean estaEnRango(int fila, int columna){
        return fila >= 0 && fila < pixeles.length && columna >= 0 && columna < pixeles[0].length;
    }


    public void resetear(){
        for(int i = 0;i<pixeles.length;i++){
            for(int j = 0;j<pixeles[i].length;j++){
                pixeles[i][j].setValor(' ');
            }
        }
    }

    public Pixel buscarPixel(char valor){
        for(int i = 0;i<pixeles.length;i++){
            for(int j = 0;j<pixeles[i].length;j++){
                if(pixeles[i][j].getValor() == valor){
                    return pixeles[i][j];
                }
            }
        }
        return null;
    }

    public Pixel getPixel(int fila, int columna){
        if(estaEnRango(fila, columna)){
            return pixeles[fila][columna];
        }
        return null;
    }

    public Pixel[][] getPixeles (){
        return pixeles;
    }

    public void mostrarFrame(){
        for(int i = 0;i<pixeles.length;i++){
            for(int j = 0;j<pixeles[i].length;j++){
                System.out.print(pixeles[i][j].getValor());
            }
            System.out.println();
        }
    }
}

class Nodo {
    private Frame dato;
    private Nodo anterior;
    private Nodo siguiente;

    public Nodo(Frame dato){
        this.dato = dato;
        this.anterior = null;
        this.siguiente = null;
    }

    public Nodo getAnterior(){
        return this.anterior;
    }
    
    public Nodo getSiguiente(){
        return this.siguiente;
    }

    public Frame getFrame(){
        return this.dato;
    }

    public void enlazarAnterior(Nodo anterior){
        this.anterior = anterior;
    }

    public void enlazarSiguiente(Nodo siguiente){
        this.siguiente = siguiente;
    }
}

class Pantalla {
    private Nodo cabeza;

    public Pantalla(){
        this.cabeza = null;
    }

    public void asignarSiguienteFrame(){
        if (cabeza != null) {
            cabeza = cabeza.getSiguiente();
        }
    }

    
    
    public void agregarFrame(Frame nuevoFrame){
        Nodo nuevoNodo = new Nodo(nuevoFrame);

        if (cabeza == null) {
            cabeza = nuevoNodo;
            cabeza.enlazarSiguiente(cabeza);
            cabeza.enlazarAnterior(cabeza);
        } else {
            Nodo ultimo = cabeza.getAnterior();
        
            ultimo.enlazarSiguiente(nuevoNodo);
            nuevoNodo.enlazarAnterior(ultimo);

            cabeza.enlazarAnterior(nuevoNodo);
            nuevoNodo.enlazarSiguiente(cabeza);
        }
    }

    public Frame getFrame(){
        return (cabeza != null) ? cabeza.getFrame() : null;
    }

    public boolean validarNodoRepetido(Frame frame){
        if (cabeza == null) return false;

        Nodo actual = cabeza;
        do {
            if (actual.getFrame() == frame){
            return true;
            }
            actual = actual.getSiguiente();
        } while (actual!=cabeza);

        return false;
    }

    public void mostrarFrame(){
        if (cabeza == null || cabeza.getFrame()== null) {
            System.out.println("No hay un frame para mostrar");
            return;
        }
        cabeza.getFrame().mostrarFrame();
    }

    public void mostrarTodosLosFrames(){
        if (cabeza==null) {
            System.out.println("Lista vacia.");
            return;
        }

        Nodo actual = cabeza;
        do {
            actual.getFrame().mostrarFrame();
            actual = actual.getSiguiente();
        } while (actual!=cabeza);
    }

    public void imprimirPantalla(){
        if (cabeza == null || cabeza.getSiguiente() == null) {
            System.out.println("No hay suficientes Frames para imprimir.");
            return;
        }

        Frame frame1 =  cabeza.getFrame();
        Frame frame2 = cabeza.getSiguiente().getFrame();

        int filas = frame1.getPixeles().length;
        int columnas = frame1.getPixeles()[0].length;

        for(int i = 0;i<filas;i++){
            StringBuilder linea = new StringBuilder();
            for(int j = 0;j<columnas;j++){
                linea.append(frame1.getPixel(i,j).getValor()).append(" ");
            }

            linea.append(" ");

            for(int j = 0;j<columnas;j++){
                linea.append(frame2.getPixel(i,j).getValor()).append(" ");
            }
            
            System.out.println(linea.toString());
        }
    }

}
