package progetto;
public class CategoriaFoglia extends Categoria {
    
	public CategoriaFoglia(String nome) {
        super(nome, null, null, null);
    }

    @Override
    public boolean isFoglia() {
        return true;
    }
}