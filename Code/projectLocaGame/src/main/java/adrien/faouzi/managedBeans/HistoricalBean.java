package adrien.faouzi.managedBeans;

import adrien.faouzi.utility.UtilityProcessing;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class HistoricalBean implements Serializable {

    private String[] historic;
    private int index;

    @PostConstruct
    public void init(){
        this.historic = new String[]{"","","",""};
        this.index = 10;
    }

    public void saveNewPageHistoric(String url){
        if(url.equals(historic[index%historic.length]))
            return;
        historic[(++index)%historic.length]=url;
    }

    public String backLastPageHistoric(){
        return historic[(--index)%historic.length];
    }


}
