/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

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
import ejb.session.stateless.enquiry.StudentEnquirySessionBeanLocal;
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
