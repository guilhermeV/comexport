package comexport.models;

public class LancamentoContabilIdView {

    public String id;

    public LancamentoContabilIdView() {
    }

    public LancamentoContabilIdView(LancamentoContabil lancamentoContabil) {
        this.id = "";
        if(lancamentoContabil != null && lancamentoContabil.getId() != null){
            this.id = lancamentoContabil.getId() != null ? lancamentoContabil.getId() : "";
        }
    }

}
