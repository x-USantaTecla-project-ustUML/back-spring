class CompositionClass{

    private Composition composition;
    private List<CompositionMethod> compositionMethod;
    private NotComposition notComposition;

    CompositionClass(){
        this.composition = new Composition();
        this.compositionMethod = this.setCompositionMethod();
    }

    void setCompositionMethod(){
        this.compositionMethod = new List<>();
    }

}