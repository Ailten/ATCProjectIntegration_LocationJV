package adrien.faouzi.projetlocagame.connexion;
//
////import javax.persistence.EntityManager;
////import javax.persistence.EntityManagerFactory;
////import javax.persistence.Persistence;
//
//public class MyEMF
//{
//    //only one instance.
//    public MyEMF(){
//        this.emfInstance = javax.persistence.Persistence.createEntityManagerFactory("locagame");
//    }
//    private static MyEMF emf;
//    public static MyEMF getEMF(){
//        return MyEMF.emf;
//    }
//    private static void initialiseEMF(){
//        if(MyEMF.emf != null)
//            return;
//        MyEMF.emf = new MyEMF();
//    }
//
//    private javax.persistence.EntityManagerFactory emfInstance;
//
//    public static javax.persistence.EntityManager getEM(){
//        MyEMF.initialiseEMF();
//        return MyEMF.getEMF().emfInstance.createEntityManager();
//    }
//
//}
