package games.stendhal.server.entity.mapstuff.block;

public class Furniture extends Block{
    private String Style;
    
    public Furniture(String style) {
        super(true, style);
        Style = style;
    }

    @Override
    public String describe(){
        try {
            int num = Integer.parseInt(Style.substring(Style.length() - 1));
            String last;
            switch (num){
                case 1:
                    last = "2";
                    break;
                case 2:
                    last = "3";
                    break;
                case 3:
                    last = "4";
                    break;
                case 4:
                    last = "1";
                    break;
                default:
                    return "Couldn't rotate the furniture";
            }
            getZone().remove(this);
            Furniture newObject = new Furniture(Style.substring(0, Style.length() - 1) + last);
            newObject.setPosition(getX(), getY());
            getZone().add(newObject);

        } catch (NumberFormatException nfe){
            return "Couldn't rotate the furniture   " + Style;
        }
        return "Rotated the furniture";
    }
}
