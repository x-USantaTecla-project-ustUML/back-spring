class CompositionClass{

    private Composition composition;
    private CompositionMethod compositionMethod;

    CompositionClass(){
        this.composition = new Composition();
        this.compositionMethod = this.setCompositionMethod();
    }

    void setCompositionMethod(){
        this.compositionMethod = new CompositionMethod();
    }

}