package softeng_project_group08.model.actions;

import java.util.LinkedList;
import java.util.List;
import softeng_project_group08.model.Action;

/**
 * Composite class implementing the Action interface and holds a collection of Action objects. 
 * This class allows the accumulation of multiple leaf classes 
 * into a complex composite class, following the Composite pattern.
 * @author group08
 */
public class MultiActions implements Action{
    private List<Action> list;

    public MultiActions() {
        this.list = new LinkedList();
    }
    
    public void addChild(Action a){
        list.add(a);
    }
    
    public void removeChild(Action a){
        list.remove(a);
    }
    
    public Action getChild(Action a){
        return list.get(list.indexOf(a));
    }
    
    
    @Override
    public void execute() {
        for(Action a : list){
            a.execute();    
        }
        
    }

    public List<Action> getList() {
        return list;
    }
    
    @Override
    public String toString(){
        String s="";
        for (Action a : list)
            s= s+a+'\n';
        return s;
    }
    
}
