/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.CountrySessionBeanLocal;
import ejb.session.stateless.FAQSessionBeanLocal;
import ejb.session.stateless.FacultySessionBeanLocal;
import ejb.session.stateless.ForumCommentSessionBeanLocal;
import ejb.session.stateless.ForumPostSessionBeanLocal;
import ejb.session.stateless.ForumTopicSessionBeanLocal;
import ejb.session.stateless.NUSModuleSessionBeanLocal;
import ejb.session.stateless.NUSchangeAdminSessionBeanLocal;
import ejb.session.stateless.PUModuleReviewSessionBeanLocal;
import ejb.session.stateless.PUModuleSessionBeanLocal;
import ejb.session.stateless.StudentSessionBeanLocal;
import ejb.session.stateless.PUReviewSessionBeanLocal;
import ejb.session.stateless.PUSessionBeanLocal;
import ejb.session.stateless.RegionSessionBeanLocal;
import ejb.session.stateless.enquiry.StudentEnquirySessionBeanLocal;
import entity.Country;
import entity.Enquiry;
import entity.FAQ;
import entity.Faculty;
import entity.ForumComment;
import entity.ForumPost;
import entity.ForumTopic;
import entity.NUSModule;
import entity.NUSchangeAdmin;
import entity.PUModule;
import entity.PUModuleReview;
import entity.Student;
import entity.PU;
import entity.PUReview;
import entity.Region;
import error.NoResultException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.enumeration.UserGroupEnum;

/**
 *
 * @author wjahoward
 */
@Singleton
@LocalBean
@Startup

public class DataInitializationSessionBean {
// hello
    @EJB
    private CountrySessionBeanLocal countrySessionBean;

    @EJB
    private RegionSessionBeanLocal regionSessionBean;

    @EJB(name = "ForumTopicSessionBeanLocal")
    private ForumTopicSessionBeanLocal forumTopicSessionBeanLocal;

    @EJB(name = "ForumPostSessionBeanLocal")
    private ForumPostSessionBeanLocal forumPostSessionBeanLocal;

    @EJB(name = "ForumCommentSessionBeanLocal")
    private ForumCommentSessionBeanLocal forumCommentSessionBeanLocal;

    @EJB
    private PUModuleReviewSessionBeanLocal pUModuleReviewSessionBean;

    @EJB
    private PUModuleSessionBeanLocal puModuleSessionBean;

    @EJB
    private StudentSessionBeanLocal studentSessionBean;

    @EJB
    private PUSessionBeanLocal pUSessionBean;

    @EJB
    private PUReviewSessionBeanLocal pUReviewSessionBean;

    @EJB
    private NUSModuleSessionBeanLocal nUSModuleSessionBean;

    @EJB
    private FacultySessionBeanLocal facultySessionBean;

    @EJB
    private NUSchangeAdminSessionBeanLocal nuschangeAdminSessionBeanLocal;

    @EJB
    private FAQSessionBeanLocal faqSessionBeanLocal;

    @EJB
    private StudentEnquirySessionBeanLocal studentEnquirySessionBeanLocal;

    @PersistenceContext(unitName = "PU-ejbPU")
    private EntityManager em;

    @PostConstruct
    public void postConstruct() {
        if (em.find(NUSchangeAdmin.class, 1L) == null) {
            try {
                initializeData();
//                createData();
            } catch (NoResultException ex) {
                Logger.getLogger(DataInitializationSessionBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void initializeData() throws NoResultException {
        NUSchangeAdmin usa1 = new NUSchangeAdmin("UserSupportAdmin1", "usa1", "password", UserGroupEnum.USER_SUPPORT);
        NUSchangeAdmin usa2 = new NUSchangeAdmin("UserSupportAdmin2", "usa2", "password", UserGroupEnum.USER_SUPPORT);
        NUSchangeAdmin ssa1 = new NUSchangeAdmin("SystemSupportAdmin1", "ssa1", "password", UserGroupEnum.SYSTEM_SUPPORT);
        NUSchangeAdmin ssa2 = new NUSchangeAdmin("SystemSupportAdmin2", "ssa2", "password", UserGroupEnum.SYSTEM_SUPPORT);

        usa1 = nuschangeAdminSessionBeanLocal.initNewNUSchangeAdmin(usa1);
        usa2 = nuschangeAdminSessionBeanLocal.initNewNUSchangeAdmin(usa2);
        nuschangeAdminSessionBeanLocal.initNewNUSchangeAdmin(ssa1);
        nuschangeAdminSessionBeanLocal.initNewNUSchangeAdmin(ssa2);

        FAQ faq1 = new FAQ("How are you?", "Hmm");
        FAQ faq2 = new FAQ("How is IS3106?", "Hmm");
        FAQ faq3 = new FAQ("How is your life?", "Hmm");

        faqSessionBeanLocal.createFAQ(faq1, usa1.getAdminId());
        faqSessionBeanLocal.createFAQ(faq2, usa1.getAdminId());
        faqSessionBeanLocal.createFAQ(faq3, usa2.getAdminId());

        Student student1 = new Student("Ben", "Leong", "90002040", "benleongrules@comp.nus.edu.sg", "password");
        Student student2 = new Student("Henry", "Chia", "90002040", "immutablepain@comp.nus.edu.sg", "password");
        Student student3 = new Student("Steven", "Halim", "90002040", "competitive@comp.nus.edu.sg", "password");

        studentSessionBean.createStudent(student1);
        studentSessionBean.createStudent(student2);
        studentSessionBean.createStudent(student3);

        PUModule module1 = new PUModule("CS2030", "Just a module with IMList everything");
        PUModule module2 = new PUModule("CS2040", "Transversal makes my head spin");

        puModuleSessionBean.createPUModule(module1);
        puModuleSessionBean.createPUModule(module2);

        PUModuleReview pumodulereview1 = new PUModuleReview("Test1", new Long(2), new Integer(1), new Integer(1));
        PUModuleReview pumodulereview2 = new PUModuleReview("Test2", new Long(2), new Integer(1), new Integer(1));

        pUModuleReviewSessionBean.createPUModuleReview(pumodulereview1);
        pUModuleReviewSessionBean.createPUModuleReview(pumodulereview2);

        Faculty faculty1 = new Faculty("SOC");
        Faculty faculty2 = new Faculty("FASS");
        Faculty faculty3 = new Faculty("FOS");

        facultySessionBean.createFaculty(faculty1);
        facultySessionBean.createFaculty(faculty2);
        facultySessionBean.createFaculty(faculty3);

        /*ForumTopic forumTopic1 = new ForumTopic("First Topic");
        ForumTopic forumTopic2 = new ForumTopic("Second Topic");
        ForumTopic forumTopic3 = new ForumTopic("Third Topic");
        ForumTopic forumTopic4 = new ForumTopic("Fourth Topic");
        ForumTopic forumTopic5 = new ForumTopic("Fifth Topic");
        
        forumTopicSessionBeanLocal.createNewForumTopic(forumTopic1, student1.getStudentId());
        forumTopicSessionBeanLocal.createNewForumTopic(forumTopic2, student2.getStudentId());
        forumTopicSessionBeanLocal.createNewForumTopic(forumTopic3, student3.getStudentId());
        forumTopicSessionBeanLocal.createNewForumTopic(forumTopic4, student1.getStudentId());
        forumTopicSessionBeanLocal.createNewForumTopic(forumTopic5, student2.getStudentId());
        
        ForumPost forumPost1 = new ForumPost("Nice Food in Korea?", "I am going Korea!!");
        ForumPost forumPost2 = new ForumPost("Nice Food in Hong Kong?", "I am going Hong Kong!!");
        ForumPost forumPost3 = new ForumPost("Places to stay?", "I want to stay in Singapore");
        
        forumPostSessionBeanLocal.createNewForumPost(forumPost1, forumTopic2.getTopicId(), student1.getStudentId());
        forumPostSessionBeanLocal.createNewForumPost(forumPost2, forumTopic2.getTopicId(), student3.getStudentId());
        forumPostSessionBeanLocal.createNewForumPost(forumPost3, forumTopic1.getTopicId(), student2.getStudentId());
        
        ForumComment forumComment1 = new ForumComment("Wow nice choice to go Korea!");
        ForumComment forumComment2 = new ForumComment("I recommend caifan");
        ForumComment forumComment3 = new ForumComment("Marina Bay Sands!");
        
        forumCommentSessionBeanLocal.createNewForumComment(forumComment1, forumPost1.getPostId(), student3.getStudentId());
        forumCommentSessionBeanLocal.createNewForumComment(forumComment2, forumPost2.getPostId(), student2.getStudentId());
        forumCommentSessionBeanLocal.createNewForumComment(forumComment3, forumPost3.getPostId(), student1.getStudentId());*/
//        Enquiry enquiry1 = new Enquiry("Hello", "Help");
//        Enquiry enquiry2 = new Enquiry("Bye", "World");
//        Enquiry enquiry3 = new Enquiry("Interesting", "Story");
//
//        studentEnquirySessionBeanLocal.createEnquiry(enquiry1, 1L);
//        studentEnquirySessionBeanLocal.createEnquiry(enquiry2, 1L);
//        studentEnquirySessionBeanLocal.createEnquiry(enquiry3, 2L);

        Region region1 = new Region("Africa");
        Region region2 = new Region("Europe");
        Region region3 = new Region("Asia");
        Region region4 = new Region("America");
        Region region5 = new Region("Australia");
        
        regionSessionBean.createNewRegion(region1);
        regionSessionBean.createNewRegion(region2);
        regionSessionBean.createNewRegion(region3);
        regionSessionBean.createNewRegion(region4);
        regionSessionBean.createNewRegion(region5);       

        Country country1 = new Country("Zimbabwe", region1);
        Country country2 = new Country("United Kingdom", region2);
        Country country3 = new Country("Japan", region3);
        Country country4 = new Country("United States", region4);
        Country country5 = new Country("Australia", region5);

        Long cId1 = countrySessionBean.createNewCountry(country1);
        Long cId2 = countrySessionBean.createNewCountry(country2);
        Long cId3 = countrySessionBean.createNewCountry(country3);
        Long cId4 = countrySessionBean.createNewCountry(country4);
        Long cId5 = countrySessionBean.createNewCountry(country5);

        PU pu1 = new PU("Africa University",
                 "Africa University is a private, Pan-African and United Methodist-related institution. It has over 1,200 students from 36 African countries.[1] It is located 17 km northwest of Mutare, Zimbabwe. It grants bachelor's, master's and PhD degrees in various programs.",
                "data:image/png;base64,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"
        );
        PU pu2 = new PU("Oxford University",
                 "The University of Oxford is a collegiate research university in Oxford, England. There is evidence of teaching as early as 1096, making it the oldest university in the English-speaking world and the world's second-oldest university in continuous operation.",
                 "https://yt3.googleusercontent.com/ytc/AL5GRJVd3R5_bH1D-OecPoAv_pKMIX_EZ0ELk2ui53ZHbQ=s900-c-k-c0x00ffffff-no-rj"
        );
        PU pu3 = new PU("University of Tokyo",
                 "The University of Tokyo (東京大学, Tōkyō daigaku; Todai or UTokyo) is a public research university in Bunkyō, Tokyo, Japan. Established in 1877, the university was the first Imperial University and is currently a Top Type university of the Top Global University Project by the Japanese government.",
                 "data:image/png;base64,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"
        );
        PU pu4 = new PU("Harvard University",
                 "Harvard University is a private Ivy League research university in Cambridge, Massachusetts. Founded in 1636 as Harvard College and named for its first benefactor, the Puritan clergyman John Harvard, it is the oldest institution of higher learning in the United States and is widely considered to be one of the most prestigious universities in the world.",
                 "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTEQXJY4guH11f13rcw8r6wH08WFJ4TXrFvX8G7w6QAvU8RAAssFgVPXkpE_bPoO4gDbCw&usqp=CAU"
        );
        PU pu5 = new PU("University of Melbourne",
                 "The University of Melbourne is a public research university located in Melbourne, Australia. Founded in 1853, it is Australia's second oldest university and the oldest in Victoria.[9] Its main campus is located in Parkville, an inner suburb north of Melbourne's central business district, with several other campuses located across Victoria.",
                 "https://upload.wikimedia.org/wikipedia/en/thumb/e/ed/Logo_of_the_University_of_Melbourne.svg/800px-Logo_of_the_University_of_Melbourne.svg.png"
        );

        Long pId1 = pUSessionBean.createNewPu(pu1, cId1, cId1);
        Long pId2 = pUSessionBean.createNewPu(pu2, cId2, cId2);
        Long pId3 = pUSessionBean.createNewPu(pu3, cId3, cId3);
        Long pId4 = pUSessionBean.createNewPu(pu4, cId4, cId4);
        Long pId5 = pUSessionBean.createNewPu(pu5, cId5, cId5);
        
        PUReview puReview1 = new PUReview(new Long(5), pId1);
        PUReview puReview2 = new PUReview(new Long(4), pId1);
        PUReview puReview3 = new PUReview(new Long(3), pId1);
        PUReview puReview4 = new PUReview(new Long(5), pId1);
        PUReview puReview5 = new PUReview(new Long(5), pId1);
        
        PUReview puReview6 = new PUReview(new Long(5), pId2);
        PUReview puReview7 = new PUReview(new Long(4), pId2);
        PUReview puReview8 = new PUReview(new Long(4), pId2);
        PUReview puReview9 = new PUReview(new Long(3), pId2);
        PUReview puReview10 = new PUReview(new Long(3), pId2);
        
        PUReview puReview11  = new PUReview(new Long(3), pId3);
        PUReview puReview12 = new PUReview(new Long(3), pId3);
        PUReview puReview13 = new PUReview(new Long(4), pId3);
        PUReview puReview14 = new PUReview(new Long(3), pId3);
        PUReview puReview15 = new PUReview(new Long(3), pId3);
        
        PUReview puReview16 = new PUReview(new Long(4), pId4);
        PUReview puReview17 = new PUReview(new Long(4), pId4);
        PUReview puReview18 = new PUReview(new Long(5), pId4);
        PUReview puReview19 = new PUReview(new Long(5), pId4);
        PUReview puReview20 = new PUReview(new Long(5), pId4);
        
        PUReview puReview21 = new PUReview(new Long(2), pId5);
        PUReview puReview22 = new PUReview(new Long(2), pId5);
        PUReview puReview23 = new PUReview(new Long(5), pId5);
        PUReview puReview24 = new PUReview(new Long(5), pId5);
        PUReview puReview25 = new PUReview(new Long(5), pId5);
        
        pUReviewSessionBean.createPUReview(puReview1, pId1);
        pUReviewSessionBean.createPUReview(puReview2, pId1);
        pUReviewSessionBean.createPUReview(puReview3, pId1);
        pUReviewSessionBean.createPUReview(puReview4, pId1);
        pUReviewSessionBean.createPUReview(puReview5, pId1);
        pUReviewSessionBean.createPUReview(puReview6, pId2);
        pUReviewSessionBean.createPUReview(puReview7, pId2);
        pUReviewSessionBean.createPUReview(puReview8, pId2);
        pUReviewSessionBean.createPUReview(puReview9, pId2);
        pUReviewSessionBean.createPUReview(puReview10, pId2);
        pUReviewSessionBean.createPUReview(puReview11, pId3);
        pUReviewSessionBean.createPUReview(puReview12, pId3);
        pUReviewSessionBean.createPUReview(puReview13, pId3);
        pUReviewSessionBean.createPUReview(puReview14, pId3);
        pUReviewSessionBean.createPUReview(puReview15, pId3);
        pUReviewSessionBean.createPUReview(puReview16, pId4);
        pUReviewSessionBean.createPUReview(puReview17, pId4);
        pUReviewSessionBean.createPUReview(puReview18, pId4);
        pUReviewSessionBean.createPUReview(puReview19, pId4);
        pUReviewSessionBean.createPUReview(puReview20, pId4);
        pUReviewSessionBean.createPUReview(puReview21, pId5);
        pUReviewSessionBean.createPUReview(puReview22, pId5);
        pUReviewSessionBean.createPUReview(puReview23, pId5);
        pUReviewSessionBean.createPUReview(puReview24, pId5);
        pUReviewSessionBean.createPUReview(puReview25, pId5);
    }

    private void createData() throws NoResultException {
        NUSModule nusModule1 = new NUSModule("IS3106", "Self-learn module");
        NUSModule nusModule2 = new NUSModule("FASS3106", "Self-learn module");
        NUSModule nusModule3 = new NUSModule("FOS3106", "Self-learn module");

        nUSModuleSessionBean.createNUSModule(nusModule1, 1L);
        nUSModuleSessionBean.createNUSModule(nusModule2, 2L);
        nUSModuleSessionBean.createNUSModule(nusModule3, 3L);

        PU pu1 = new PU("ABC", "DEF", "GHI");

        pUSessionBean.createNewPu(pu1);

        PUReview pUReview1 = new PUReview(1L, "Meh", 2, 100, false);
        PUReview pUReview2 = new PUReview(2L, "Meh", 3, 10, false);
        PUReview pUReview3 = new PUReview(3L, "Meh", 4, 20, true);

        pUReviewSessionBean.createPUReview(pUReview1, 1L);
        pUReviewSessionBean.createPUReview(pUReview2, 1L);
        pUReviewSessionBean.createPUReview(pUReview3, 1L);
    }

}
