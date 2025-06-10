package adrien.faouzi.managedBeans;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ApplicationScoped
public class HomePageBean implements Serializable {

    private List<String> listImage;

    private String randomImageForHomePage;

    @PostConstruct
    public void init(){
        listImage = new ArrayList<>();
        listImage.add("ImageCarousel01.jpg");
        listImage.add("ImageCarousel02.jpg");
        listImage.add("ImageCarousel03.jpg");
    }

    public String getRandomImageForHomePage(){
        return listImage.get((int)Math.floor(Math.random() * listImage.size()));
    }

}
