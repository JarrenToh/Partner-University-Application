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
import ejb.session.stateless.PUReviewSessionBeanLocal;
import ejb.session.stateless.PUSessionBeanLocal;
import ejb.session.stateless.RegionSessionBeanLocal;
import ejb.session.stateless.StudentSessionBeanLocal;
import ejb.session.stateless.enquiry.StudentEnquirySessionBeanLocal;
import entity.Country;
import entity.FAQ;
import entity.Faculty;
import entity.ForumComment;
import entity.ForumPost;
import entity.ForumTopic;
import entity.NUSModule;
import entity.NUSchangeAdmin;
import entity.PU;
import entity.PUModule;
import entity.PUModuleReview;
import entity.PUReview;
import entity.Region;
import entity.Student;
import error.NoResultException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
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

    @EJB
    private PUReviewSessionBeanLocal pUReviewSessionBean1;

    @EJB
    private RegionSessionBeanLocal regionSessionBean;

    @EJB
    private CountrySessionBeanLocal countrySessionBean;

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

    @EJB(name = "StudentEnquirySessionBeanLocal")
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
                Logger
                        .getLogger(DataInitializationSessionBean.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
    }

    private void initializeData() throws NoResultException {
        NUSchangeAdmin usa1 = new NUSchangeAdmin(
                "UserSupportAdmin1",
                "usa1",
                "password",
                UserGroupEnum.USER_SUPPORT
        );
        NUSchangeAdmin usa2 = new NUSchangeAdmin(
                "UserSupportAdmin2",
                "usa2",
                "password",
                UserGroupEnum.USER_SUPPORT
        );
        NUSchangeAdmin ssa1 = new NUSchangeAdmin(
                "SystemSupportAdmin1",
                "ssa1",
                "password",
                UserGroupEnum.SYSTEM_SUPPORT
        );
        NUSchangeAdmin ssa2 = new NUSchangeAdmin(
                "SystemSupportAdmin2",
                "ssa2",
                "password",
                UserGroupEnum.SYSTEM_SUPPORT
        );

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

        Student student1 = new Student(
                "Ben",
                "Leong",
                "90002040",
                "benleongrules@comp.nus.edu.sg",
                "password"
        );
        Student student2 = new Student(
                "Henry",
                "Chia",
                "90002040",
                "immutablepain@comp.nus.edu.sg",
                "password"
        );
        Student student3 = new Student(
                "Steven",
                "Halim",
                "90002040",
                "competitive@comp.nus.edu.sg",
                "password"
        );
        Student student4 = new Student(
                "Sarah",
                "Tan",
                "90002041",
                "sarahtan@comp.nus.edu.sg",
                "password"
        );
        Student student5 = new Student(
                "Jason",
                "Lee",
                "90002042",
                "jasonlee@comp.nus.edu.sg",
                "password"
        );
        Student student6 = new Student(
                "Jasmine",
                "Lim",
                "90002043",
                "jasminelim@comp.nus.edu.sg",
                "password"
        );
        Student student7 = new Student(
                "Alex",
                "Koh",
                "90002044",
                "alexkoh@comp.nus.edu.sg",
                "password"
        );
        Student student8 = new Student(
                "Grace",
                "Teo",
                "90002045",
                "graceteo@comp.nus.edu.sg",
                "password"
        );
        Student student9 = new Student(
                "David",
                "Ng",
                "90002046",
                "davidng@comp.nus.edu.sg",
                "password"
        );
        Student student10 = new Student(
                "Jessica",
                "Wong",
                "90002047",
                "jessicawong@comp.nus.edu.sg",
                "password"
        );
        Student student11 = new Student(
                "Alan",
                "Lim",
                "90002048",
                "alanlim@comp.nus.edu.sg",
                "password"
        );
        Student student12 = new Student(
                "Lily",
                "Tan",
                "90002049",
                "lilytan@comp.nus.edu.sg",
                "password"
        );
        Student student13 = new Student(
                "Kevin",
                "Chen",
                "90002050",
                "kevinchen@comp.nus.edu.sg",
                "password"
        );
        Student student14 = new Student(
                "Amy",
                "Tan",
                "90002051",
                "amytan@comp.nus.edu.sg",
                "password"
        );
        Student student15 = new Student(
                "Brian",
                "Lim",
                "90002052",
                "brianlim@comp.nus.edu.sg",
                "password"
        );
        Student student16 = new Student(
                "Emily",
                "Ng",
                "90002053",
                "emilyng@comp.nus.edu.sg",
                "password"
        );
        Student student17 = new Student(
                "Victor",
                "Koh",
                "90002054",
                "victorkoh@comp.nus.edu.sg",
                "password"
        );
        Student student18 = new Student(
                "Natalie",
                "Tan",
                "90002055",
                "natalietan@comp.nus.edu.sg",
                "password"
        );
        Student student19 = new Student(
                "Samuel",
                "Tan",
                "90002056",
                "samueltan@comp.nus.edu.sg",
                "password"
        );
        Student student20 = new Student(
                "Cindy",
                "Lee",
                "90002057",
                "cindylee@comp.nus.edu.sg",
                "password"
        );
        Student student21 = new Student(
                "John",
                "Doe",
                "90002041",
                "johndoe@comp.nus.edu.sg",
                "password"
        );
        Student student22 = new Student(
                "Jane",
                "Doe",
                "90002042",
                "janedoe@comp.nus.edu.sg",
                "password"
        );
        Student student23 = new Student(
                "David",
                "Smith",
                "90002043",
                "davidsmith@comp.nus.edu.sg",
                "password"
        );
        Student student24 = new Student(
                "Sarah",
                "Johnson",
                "90002044",
                "sarahjohnson@comp.nus.edu.sg",
                "password"
        );
        Student student25 = new Student(
                "Michael",
                "Lee",
                "90002045",
                "michaellee@comp.nus.edu.sg",
                "password"
        );
        Student student26 = new Student(
                "Emily",
                "Wong",
                "90002046",
                "emilywong@comp.nus.edu.sg",
                "password"
        );
        Student student27 = new Student(
                "Daniel",
                "Tan",
                "90002047",
                "danieltan@comp.nus.edu.sg",
                "password"
        );
        Student student28 = new Student(
                "Rachel",
                "Lim",
                "90002048",
                "rachellim@comp.nus.edu.sg",
                "password"
        );
        Student student29 = new Student(
                "William",
                "Ng",
                "90002049",
                "williamng@comp.nus.edu.sg",
                "password"
        );
        Student student30 = new Student(
                "Michelle",
                "Tan",
                "90002050",
                "michelletan@comp.nus.edu.sg",
                "password"
        );
        Student student31 = new Student(
                "Andrew",
                "Chen",
                "90002051",
                "andrewchen@comp.nus.edu.sg",
                "password"
        );
        Student student32 = new Student(
                "Melissa",
                "Koh",
                "90002052",
                "melissakoh@comp.nus.edu.sg",
                "password"
        );
        Student student33 = new Student(
                "Jason",
                "Ong",
                "90002053",
                "jasonong@comp.nus.edu.sg",
                "password"
        );
        Student student34 = new Student(
                "Samantha",
                "Teo",
                "90002054",
                "samanthateo@comp.nus.edu.sg",
                "password"
        );
        Student student35 = new Student(
                "Nicholas",
                "Tan",
                "90002055",
                "nicholastan@comp.nus.edu.sg",
                "password"
        );
        Student student36 = new Student(
                "Amanda",
                "Chua",
                "90002056",
                "amandachua@comp.nus.edu.sg",
                "password"
        );
        Student student37 = new Student(
                "Kevin",
                "Ng",
                "90002057",
                "kevinng@comp.nus.edu.sg",
                "password"
        );
        Student student38 = new Student(
                "Grace",
                "Loh",
                "90002058",
                "graceloh@comp.nus.edu.sg",
                "password"
        );
        Student student39 = new Student(
                "Justin",
                "Lim",
                "90002059",
                "justinlim@comp.nus.edu.sg",
                "password"
        );
        Student student40 = new Student(
                "Hannah",
                "Tan",
                "90002060",
                "hannahtan@comp.nus.edu.sg",
                "password"
        );
        Student student41 = new Student(
                "Avery",
                "Ng",
                "90002043",
                "averyng@comp.nus.edu.sg",
                "password"
        );

        Student student42 = new Student(
                "Brenda",
                "Tan",
                "90002044",
                "brendatan@comp.nus.edu.sg",
                "password"
        );

        Student student43 = new Student(
                "Caleb",
                "Koh",
                "90002045",
                "calebkoh@comp.nus.edu.sg",
                "password"
        );

        Student student44 = new Student(
                "Daniel",
                "Lim",
                "90002046",
                "daniellim@comp.nus.edu.sg",
                "password"
        );

        Student student45 = new Student(
                "Ella",
                "Ng",
                "90002047",
                "ellang@comp.nus.edu.sg",
                "password"
        );

        Student student46 = new Student(
                "Aiden",
                "Tan",
                "90002041",
                "aiden.tan@comp.nus.edu.sg",
                "password"
        );
        Student student47 = new Student(
                "Oliver",
                "Chen",
                "90002042",
                "oliver.chen@comp.nus.edu.sg",
                "password"
        );
        Student student48 = new Student(
                "Isabella",
                "Wong",
                "90002043",
                "isabella.wong@comp.nus.edu.sg",
                "password"
        );
        Student student49 = new Student(
                "Ethan",
                "Ng",
                "90002044",
                "ethan.ng@comp.nus.edu.sg",
                "password"
        );
        Student student50 = new Student(
                "Sophia",
                "Ong",
                "90002045",
                "sophia.ong@comp.nus.edu.sg",
                "password"
        );
        Student student51 = new Student(
                "Mason",
                "Lim",
                "90002046",
                "mason.lim@comp.nus.edu.sg",
                "password"
        );
        Student student52 = new Student(
                "Ava",
                "Koh",
                "90002047",
                "ava.koh@comp.nus.edu.sg",
                "password"
        );
        Student student53 = new Student(
                "Noah",
                "Tan",
                "90002048",
                "noah.tan@comp.nus.edu.sg",
                "password"
        );
        Student student54 = new Student(
                "Emma",
                "Chew",
                "90002049",
                "emma.chew@comp.nus.edu.sg",
                "password"
        );
        Student student55 = new Student(
                "Liam",
                "Tan",
                "90002050",
                "liam.tan@comp.nus.edu.sg",
                "password"
        );
        Student student56 = new Student(
                "Chloe",
                "Lim",
                "90002051",
                "chloe.lim@comp.nus.edu.sg",
                "password"
        );
        Student student57 = new Student(
                "Lucas",
                "Lee",
                "90002052",
                "lucas.lee@comp.nus.edu.sg",
                "password"
        );
        Student student58 = new Student(
                "Zoe",
                "Ng",
                "90002053",
                "zoe.ng@comp.nus.edu.sg",
                "password"
        );
        Student student59 = new Student(
                "Logan",
                "Chin",
                "90002054",
                "logan.chin@comp.nus.edu.sg",
                "password"
        );
        Student student60 = new Student(
                "Lily",
                "Tan",
                "90002055",
                "lily.tan@comp.nus.edu.sg",
                "password"
        );
        Student student61 = new Student(
                "Caleb",
                "Goh",
                "90002056",
                "caleb.goh@comp.nus.edu.sg",
                "password"
        );
        Student student62 = new Student(
                "Mia",
                "Liew",
                "90002057",
                "mia.liew@comp.nus.edu.sg",
                "password"
        );
        Student student63 = new Student(
                "Jackson",
                "Lim",
                "90002058",
                "jackson.lim@comp.nus.edu.sg",
                "password"
        );
        Student student64 = new Student(
                "Sophie",
                "Ng",
                "90002090",
                "sophieng@comp.nus.edu.sg",
                "password"
        );

        Student student65 = new Student(
                "Marcus",
                "Koh",
                "90002091",
                "marcuskoh@comp.nus.edu.sg",
                "password"
        );

        Student student66 = new Student(
                "Elena",
                "Tan",
                "90002092",
                "elenatan@comp.nus.edu.sg",
                "password"
        );

        Student student67 = new Student(
                "Louis",
                "Lee",
                "90002093",
                "louisleelw@comp.nus.edu.sg",
                "password"
        );

        Student student68 = new Student(
                "Nadia",
                "Lim",
                "90002094",
                "nadialim@comp.nus.edu.sg",
                "password"
        );

        Student student69 = new Student(
                "Aiden",
                "Tan",
                "90002095",
                "aiden.tan@comp.nus.edu.sg",
                "password"
        );

        Student student70 = new Student(
                "Olivia",
                "Ng",
                "90002096",
                "oliviang@comp.nus.edu.sg",
                "password"
        );

        Student student71 = new Student(
                "Dylan",
                "Tay",
                "90002097",
                "dylantay@comp.nus.edu.sg",
                "password"
        );

        Student student72 = new Student(
                "Amelia",
                "Liu",
                "90002098",
                "amelialiu@comp.nus.edu.sg",
                "password"
        );

        Student student73 = new Student(
                "William",
                "Lim",
                "90002099",
                "williamlim@comp.nus.edu.sg",
                "password"
        );

        Student student74 = new Student(
                "Samantha",
                "Wong",
                "90002100",
                "samanthawong@comp.nus.edu.sg",
                "password"
        );

        Student student75 = new Student(
                "Jayden",
                "Chew",
                "90002101",
                "jaydenchew@comp.nus.edu.sg",
                "password"
        );

        Student student76 = new Student(
                "Ella",
                "Teo",
                "90002102",
                "ellateo@comp.nus.edu.sg",
                "password"
        );

        Student student77 = new Student(
                "Gabriel",
                "Loh",
                "90002103",
                "gabrielloh@comp.nus.edu.sg",
                "password"
        );

        Student student78 = new Student(
                "Hannah",
                "Koh",
                "90002104",
                "hannahkoh@comp.nus.edu.sg",
                "password"
        );

        Student student79 = new Student(
                "Nicholas",
                "Tan",
                "90002105",
                "nicholastan@comp.nus.edu.sg",
                "password"
        );

        Student student80 = new Student(
                "Isabelle",
                "Yeo",
                "90002106",
                "isabelleyeo@comp.nus.edu.sg",
                "password"
        );

        Student student81 = new Student(
                "Alexander",
                "Chen",
                "90002107",
                "alexanderchen@comp.nus.edu.sg",
                "password"
        );
        
        Student student82 = new Student(
                "Jason",
                "Ong",
                "90002128",
                "jasonong@comp.nus.edu.sg",
                "password"
        );

        Student student83 = new Student(
                "Annie",
                "Ng",
                "90002083",
                "annieng@comp.nus.edu.sg",
                "password"
        );

        Student student84 = new Student(
                "Daniel",
                "Lim",
                "90002084",
                "daniellim@comp.nus.edu.sg",
                "password"
        );

        Student student85 = new Student(
                "Grace",
                "Tan",
                "90002085",
                "gracetan@comp.nus.edu.sg",
                "password"
        );

        Student student86 = new Student(
                "Kevin",
                "Tan",
                "90002086",
                "kevintan@comp.nus.edu.sg",
                "password"
        );

        Student student87 = new Student(
                "Lucas",
                "Wong",
                "90002087",
                "lucaswong@comp.nus.edu.sg",
                "password"
        );

        Student student88 = new Student(
                "Natalie",
                "Chen",
                "90002088",
                "nataliechen@comp.nus.edu.sg",
                "password"
        );

        Student student89 = new Student(
                "Oscar",
                "Yeo",
                "90002089",
                "oscaryeo@comp.nus.edu.sg",
                "password"
        );

        Student student90 = new Student(
                "Peter",
                "Goh",
                "90002090",
                "petergoh@comp.nus.edu.sg",
                "password"
        );

        Student student91 = new Student(
                "Rachel",
                "Lee",
                "90002091",
                "rachellee@comp.nus.edu.sg",
                "password"
        );

        Student student92 = new Student(
                "Samuel",
                "Ng",
                "90002092",
                "samuelng@comp.nus.edu.sg",
                "password"
        );

        Student student93 = new Student(
                "Tiffany",
                "Koh",
                "90002093",
                "tiffanykoh@comp.nus.edu.sg",
                "password"
        );

        Student student94 = new Student(
                "Victor",
                "Tan",
                "90002094",
                "victortan@comp.nus.edu.sg",
                "password"
        );

        Student student95 = new Student(
                "Wendy",
                "Lau",
                "90002095",
                "wendylau@comp.nus.edu.sg",
                "password"
        );

        Student student96 = new Student(
                "Xavier",
                "Kang",
                "90002096",
                "xavierkang@comp.nus.edu.sg",
                "password"
        );

        Student student97 = new Student(
                "Yvonne",
                "Teo",
                "90002097",
                "yvonneteo@comp.nus.edu.sg",
                "password"
        );

        Student student98 = new Student(
                "Zoe",
                "Lim",
                "90002098",
                "zoelim@comp.nus.edu.sg",
                "password"
        );

        Student student99 = new Student(
                "Adam",
                "Chin",
                "90002099",
                "adamchin@comp.nus.edu.sg",
                "password"
        );

        Student student100 = new Student(
                "Brenda",
                "Chong",
                "90002100",
                "brendachong@comp.nus.edu.sg",
                "password"
        );

        Student student101 = new Student(
                "Alice",
                "Ng",
                "90002101",
                "aliceng@comp.nus.edu.sg",
                "password"
        );

        Student student102 = new Student(
                "Bob",
                "Chen",
                "90002102",
                "bobchen@comp.nus.edu.sg",
                "password"
        );

        Student student103 = new Student(
                "Carol",
                "Lim",
                "90002103",
                "carollim@comp.nus.edu.sg",
                "password"
        );

        Student student104 = new Student(
                "David",
                "Wong",
                "90002104",
                "davidwong@comp.nus.edu.sg",
                "password"
        );

        Student student105 = new Student(
                "Evelyn",
                "Tan",
                "90002105",
                "evelyntan@comp.nus.edu.sg",
                "password"
        );

        Student student106 = new Student(
                "Frank",
                "Chong",
                "90002106",
                "frankchong@comp.nus.edu.sg",
                "password"
        );

        Student student107 = new Student(
                "Grace",
                "Lim",
                "90002107",
                "gracelim@comp.nus.edu.sg",
                "password"
        );

        Student student108 = new Student(
                "Henry",
                "Tan",
                "90002108",
                "henrytan@comp.nus.edu.sg",
                "password"
        );

        Student student109 = new Student(
                "Ivy",
                "Ng",
                "90002109",
                "ivyng@comp.nus.edu.sg",
                "password"
        );

        Student student110 = new Student(
                "Jason",
                "Lim",
                "90002110",
                "jasonlim@comp.nus.edu.sg",
                "password"
        );

        Student student111 = new Student(
                "Karen",
                "Chen",
                "90002111",
                "karenchen@comp.nus.edu.sg",
                "password"
        );

        Student student112 = new Student(
                "Larry",
                "Wong",
                "90002112",
                "larrywong@comp.nus.edu.sg",
                "password"
        );

        Student student113 = new Student(
                "Michelle",
                "Tan",
                "90002113",
                "michelletan@comp.nus.edu.sg",
                "password"
        );

        Student student114 = new Student(
                "Nancy",
                "Chong",
                "90002114",
                "nancychong@comp.nus.edu.sg",
                "password"
        );

        Student student115 = new Student(
                "Oscar",
                "Ng",
                "90002115",
                "oscarng@comp.nus.edu.sg",
                "password"
        );

        Student student116 = new Student(
                "Peter",
                "Lim",
                "90002116",
                "peterlim@comp.nus.edu.sg",
                "password"
        );

        Student student117 = new Student(
                "Queenie",
                "Tan",
                "90002117",
                "queentan@comp.nus.edu.sg",
                "password"
        );

        Student student118 = new Student(
                "Rachel",
                "Chen",
                "90002118",
                "rachelchen@comp.nus.edu.sg",
                "password"
        );

        Student student119 = new Student(
                "Evelyn",
                "Wong",
                "90002119",
                "evelynwong@comp.nus.edu.sg",
                "password"
        );

        Student student120 = new Student(
                "Rachel",
                "Lim",
                "90002069",
                "rachellim@comp.nus.edu.sg",
                "password"
        );
        Student student121 = new Student(
                "Mark",
                "Ng",
                "90002070",
                "markng@comp.nus.edu.sg",
                "password"
        );
        Student student122 = new Student(
                "Emily",
                "Tan",
                "90002071",
                "emilytan@comp.nus.edu.sg",
                "password"
        );
        Student student123 = new Student(
                "Jack",
                "Wong",
                "90002072",
                "jackwong@comp.nus.edu.sg",
                "password"
        );
        Student student124 = new Student(
                "Nadia",
                "Lim",
                "90002073",
                "nadialim@comp.nus.edu.sg",
                "password"
        );
        Student student125 = new Student(
                "Aryan",
                "Lee",
                "90002074",
                "aryanlee@comp.nus.edu.sg",
                "password"
        );
        Student student126 = new Student(
                "Sophie",
                "Chen",
                "90002075",
                "sophiechen@comp.nus.edu.sg",
                "password"
        );
        Student student127 = new Student(
                "Thomas",
                "Goh",
                "90002076",
                "thomasgoh@comp.nus.edu.sg",
                "password"
        );
        Student student128 = new Student(
                "Caitlin",
                "Tay",
                "90002077",
                "caitlintay@comp.nus.edu.sg",
                "password"
        );
        Student student129 = new Student(
                "Yan",
                "Tan",
                "90002078",
                "yantan@comp.nus.edu.sg",
                "password"
        );
        Student student130 = new Student(
                "Marcus",
                "Chua",
                "90002079",
                "marcuschua@comp.nus.edu.sg",
                "password"
        );
        Student student131 = new Student(
                "Bella",
                "Koh",
                "90002080",
                "bellakoh@comp.nus.edu.sg",
                "password"
        );
        Student student132 = new Student(
                "Brandon",
                "Ong",
                "90002081",
                "brandonong@comp.nus.edu.sg",
                "password"
        );
        Student student133 = new Student(
                "Amelia",
                "Teo",
                "90002082",
                "ameliateo@comp.nus.edu.sg",
                "password"
        );
        Student student134 = new Student(
                "Alex",
                "Lim",
                "90002083",
                "alexlim@comp.nus.edu.sg",
                "password"
        );
        Student student135 = new Student(
                "Hannah",
                "Goh",
                "90002084",
                "hannahgoh@comp.nus.edu.sg",
                "password"
        );
        Student student136 = new Student(
                "Isaac",
                "Koh",
                "90002085",
                "isaackoh@comp.nus.edu.sg",
                "password"
        );
        Student student137 = new Student(
                "Natalie",
                "Chong",
                "90002086",
                "nataliechong@comp.nus.edu.sg",
                "password"
        );

        Long studentId1 = studentSessionBean.createStudent(student1);
        Long studentId2 = studentSessionBean.createStudent(student2);
        Long studentId3 = studentSessionBean.createStudent(student3);

        Long studentId4 = studentSessionBean.createStudent(student4);
        Long studentId5 = studentSessionBean.createStudent(student5);
        Long studentId6 = studentSessionBean.createStudent(student6);
        Long studentId7 = studentSessionBean.createStudent(student7);
        Long studentId8 = studentSessionBean.createStudent(student8);
        Long studentId9 = studentSessionBean.createStudent(student9);
        Long studentId10 = studentSessionBean.createStudent(student10);
        Long studentId11 = studentSessionBean.createStudent(student11);
        Long studentId12 = studentSessionBean.createStudent(student12);
        Long studentId13 = studentSessionBean.createStudent(student13);
        Long studentId14 = studentSessionBean.createStudent(student14);
        Long studentId15 = studentSessionBean.createStudent(student15);
        Long studentId16 = studentSessionBean.createStudent(student16);
        Long studentId17 = studentSessionBean.createStudent(student17);
        Long studentId18 = studentSessionBean.createStudent(student18);
        Long studentId19 = studentSessionBean.createStudent(student19);
        Long studentId20 = studentSessionBean.createStudent(student20);
        Long studentId21 = studentSessionBean.createStudent(student21);
        Long studentId22 = studentSessionBean.createStudent(student22);
        Long studentId23 = studentSessionBean.createStudent(student23);
        Long studentId24 = studentSessionBean.createStudent(student24);
        Long studentId25 = studentSessionBean.createStudent(student25);
        Long studentId26 = studentSessionBean.createStudent(student26);
        Long studentId27 = studentSessionBean.createStudent(student27);
        Long studentId28 = studentSessionBean.createStudent(student28);
        Long studentId29 = studentSessionBean.createStudent(student29);
        Long studentId30 = studentSessionBean.createStudent(student30);
        Long studentId31 = studentSessionBean.createStudent(student31);
        Long studentId32 = studentSessionBean.createStudent(student32);
        Long studentId33 = studentSessionBean.createStudent(student33);
        Long studentId34 = studentSessionBean.createStudent(student34);
        Long studentId35 = studentSessionBean.createStudent(student35);
        Long studentId36 = studentSessionBean.createStudent(student36);
        Long studentId37 = studentSessionBean.createStudent(student37);
        Long studentId38 = studentSessionBean.createStudent(student38);
        Long studentId39 = studentSessionBean.createStudent(student39);
        Long studentId40 = studentSessionBean.createStudent(student40);
        Long studentId41 = studentSessionBean.createStudent(student41);
        Long studentId42 = studentSessionBean.createStudent(student42);
        Long studentId43 = studentSessionBean.createStudent(student43);
        Long studentId44 = studentSessionBean.createStudent(student44);
        Long studentId45 = studentSessionBean.createStudent(student45);
        Long studentId46 = studentSessionBean.createStudent(student46);
        Long studentId47 = studentSessionBean.createStudent(student47);
        Long studentId48 = studentSessionBean.createStudent(student48);
        Long studentId49 = studentSessionBean.createStudent(student49);
        Long studentId50 = studentSessionBean.createStudent(student50);
        Long studentId51 = studentSessionBean.createStudent(student51);
        Long studentId52 = studentSessionBean.createStudent(student52);
        Long studentId53 = studentSessionBean.createStudent(student53);
        Long studentId54 = studentSessionBean.createStudent(student54);
        Long studentId55 = studentSessionBean.createStudent(student55);
        Long studentId56 = studentSessionBean.createStudent(student56);
        Long studentId57 = studentSessionBean.createStudent(student57);
        Long studentId58 = studentSessionBean.createStudent(student58);
        Long studentId59 = studentSessionBean.createStudent(student59);
        Long studentId60 = studentSessionBean.createStudent(student60);
        Long studentId61 = studentSessionBean.createStudent(student61);
        Long studentId62 = studentSessionBean.createStudent(student62);
        Long studentId63 = studentSessionBean.createStudent(student63);
        Long studentId64 = studentSessionBean.createStudent(student64);
        Long studentId65 = studentSessionBean.createStudent(student65);
        Long studentId66 = studentSessionBean.createStudent(student66);
        Long studentId67 = studentSessionBean.createStudent(student67);
        Long studentId68 = studentSessionBean.createStudent(student68);
        Long studentId69 = studentSessionBean.createStudent(student69);
        Long studentId70 = studentSessionBean.createStudent(student70);
        Long studentId71 = studentSessionBean.createStudent(student71);
        Long studentId72 = studentSessionBean.createStudent(student72);
        Long studentId73 = studentSessionBean.createStudent(student73);
        Long studentId74 = studentSessionBean.createStudent(student74);
        Long studentId75 = studentSessionBean.createStudent(student75);
        Long studentId76 = studentSessionBean.createStudent(student76);
        Long studentId77 = studentSessionBean.createStudent(student77);
        Long studentId78 = studentSessionBean.createStudent(student78);
        Long studentId79 = studentSessionBean.createStudent(student79);
        Long studentId80 = studentSessionBean.createStudent(student80);
        Long studentId81 = studentSessionBean.createStudent(student81);
        Long studentId82 = studentSessionBean.createStudent(student82);
        Long studentId83 = studentSessionBean.createStudent(student83);
        Long studentId84 = studentSessionBean.createStudent(student84);
        Long studentId85 = studentSessionBean.createStudent(student85);
        Long studentId86 = studentSessionBean.createStudent(student86);
        Long studentId87 = studentSessionBean.createStudent(student87);
        Long studentId88 = studentSessionBean.createStudent(student88);
        Long studentId89 = studentSessionBean.createStudent(student89);
        Long studentId90 = studentSessionBean.createStudent(student90);
        Long studentId91 = studentSessionBean.createStudent(student91);
        Long studentId92 = studentSessionBean.createStudent(student92);
        Long studentId93 = studentSessionBean.createStudent(student93);
        Long studentId94 = studentSessionBean.createStudent(student94);
        Long studentId95 = studentSessionBean.createStudent(student95);
        Long studentId96 = studentSessionBean.createStudent(student96);
        Long studentId97 = studentSessionBean.createStudent(student97);
        Long studentId98 = studentSessionBean.createStudent(student98);
        Long studentId99 = studentSessionBean.createStudent(student99);
        Long studentId100 = studentSessionBean.createStudent(student100);
        Long studentId101 = studentSessionBean.createStudent(student101);
        Long studentId102 = studentSessionBean.createStudent(student102);
        Long studentId103 = studentSessionBean.createStudent(student103);
        Long studentId104 = studentSessionBean.createStudent(student104);
        Long studentId105 = studentSessionBean.createStudent(student105);
        Long studentId106 = studentSessionBean.createStudent(student106);
        Long studentId107 = studentSessionBean.createStudent(student107);
        Long studentId108 = studentSessionBean.createStudent(student108);
        Long studentId109 = studentSessionBean.createStudent(student109);
        Long studentId110 = studentSessionBean.createStudent(student110);
        Long studentId111 = studentSessionBean.createStudent(student111);
        Long studentId112 = studentSessionBean.createStudent(student112);
        Long studentId113 = studentSessionBean.createStudent(student113);
        Long studentId114 = studentSessionBean.createStudent(student114);
        Long studentId115 = studentSessionBean.createStudent(student115);
        Long studentId116 = studentSessionBean.createStudent(student116);
        Long studentId117 = studentSessionBean.createStudent(student117);
        Long studentId118 = studentSessionBean.createStudent(student118);
        Long studentId119 = studentSessionBean.createStudent(student119);
        Long studentId120 = studentSessionBean.createStudent(student120);
        Long studentId121 = studentSessionBean.createStudent(student121);
        Long studentId122 = studentSessionBean.createStudent(student122);
        Long studentId123 = studentSessionBean.createStudent(student123);
        Long studentId124 = studentSessionBean.createStudent(student124);
        Long studentId125 = studentSessionBean.createStudent(student125);
        Long studentId126 = studentSessionBean.createStudent(student126);
        Long studentId127 = studentSessionBean.createStudent(student127);
        Long studentId128 = studentSessionBean.createStudent(student128);
        Long studentId129 = studentSessionBean.createStudent(student129);
        Long studentId130 = studentSessionBean.createStudent(student130);
        Long studentId131 = studentSessionBean.createStudent(student131);
        Long studentId132 = studentSessionBean.createStudent(student132);
        Long studentId133 = studentSessionBean.createStudent(student133);
        Long studentId134 = studentSessionBean.createStudent(student134);
        Long studentId135 = studentSessionBean.createStudent(student135);
        Long studentId136 = studentSessionBean.createStudent(student136);
        Long studentId137 = studentSessionBean.createStudent(student137);

        Faculty faculty1 = new Faculty(
                "Faculty of Arts and Social Sciences (FASS)"
        );
        Faculty faculty2 = new Faculty("School of Business (NUS Business School)");
        Faculty faculty3 = new Faculty("School of Computing (SoC)");
        Faculty faculty4 = new Faculty("Faculty of Dentistry (FoD)");
        Faculty faculty5 = new Faculty("School of Design and Environment (SDE)");
        Faculty faculty6 = new Faculty("Faculty of Engineering (FoE)");
        Faculty faculty7 = new Faculty("Faculty of Law (FoL)");
        Faculty faculty8 = new Faculty(
                "Yong Loo Lin School of Medicine (NUS Medicine)"
        );
        Faculty faculty9 = new Faculty("Faculty of Science (FoS)");

        Long facultyId1 = facultySessionBean.createFaculty(faculty1);
        Long facultyId2 = facultySessionBean.createFaculty(faculty2);
        Long facultyId3 = facultySessionBean.createFaculty(faculty3);
        Long facultyId4 = facultySessionBean.createFaculty(faculty4);
        Long facultyId5 = facultySessionBean.createFaculty(faculty5);
        Long facultyId6 = facultySessionBean.createFaculty(faculty6);
        Long facultyId7 = facultySessionBean.createFaculty(faculty7);
        Long facultyId8 = facultySessionBean.createFaculty(faculty8);
        Long facultyId9 = facultySessionBean.createFaculty(faculty9);

        NUSModule nusModule1 = new NUSModule(
                "Enterprise Systems Interface Design and Development",
                "IS3106",
                "This module aims to train students to be conversant in front-end development for Enterprise Systems. It complements IS2103 which focuses on backend development aspects for Enterprise Systems. Topics covered include web development scripting languages, web templating design and component design, integrating with backend application, and basic mobile application development."
        );
        NUSModule nusModule2 = new NUSModule(
                "Computational Thinking",
                "CS1010",
                "The module provides a gentle introduction to programming using Python programming language. It aims to provide students with an understanding of the role computation can play in solving problems. It also aims to help students, regardless of their major, to feel justifiably confident of their ability to write small programs that allow them to accomplish useful goals."
        );
        NUSModule nusModule3 = new NUSModule(
                "Data Structures and Algorithms",
                "CS2040",
                "This module is about the design, analysis, and implementation of data structures and algorithms. It covers classical data structures (linked lists, stacks, queues, trees, and graphs), and algorithms (sorting, searching, and graph traversal). It also includes an introduction to algorithmic complexity and intractability."
        );
        NUSModule nusModule4 = new NUSModule(
                "Programming Methodology",
                "CS1101S",
                "This module aims to teach students how to program systematically, and how to develop good programming habits. It uses a variant of the Racket programming language to solidify the concepts learned in class."
        );
        NUSModule nusModule5 = new NUSModule(
                "Programming Methodology II",
                "CS2030",
                "This module aims to teach students how to design and develop larger programs, and how to write programs that are more reliable, efficient, and maintainable. It covers topics such as abstraction, interfaces, data representation, and program correctness. It also includes an introduction to concurrent programming."
        );
        NUSModule nusModule6 = new NUSModule(
                "Introduction to Computer Networks",
                "CS2105",
                "This module provides an introduction to computer networks, covering the layered architecture of computer networks and the services and protocols that are provided at each layer."
        );
        NUSModule nusModule7 = new NUSModule(
                "Database Systems",
                "CS2102",
                "This module covers the principles and techniques of database systems. It includes topics such as data models, relational algebra, SQL, and transaction processing. It also covers topics such as query optimization, concurrency control, and recovery."
        );
        NUSModule nusModule8 = new NUSModule(
                "Operating Systems",
                "CS2106",
                "This module covers the principles and concepts of operating systems, including process management, memory management, file systems, and input/output."
        );
        NUSModule nusModule9 = new NUSModule(
                "Computer Security",
                "CS2107",
                "This module covers the principles and techniques of computer security. It includes topics such as access control, authentication, confidentiality, integrity, and availability. It also covers topics such as network security, web security, and malware."
        );
        NUSModule nusModule10 = new NUSModule(
                "Software Engineering",
                "CS2103T",
                "This module covers the principles and practices of software engineering, including requirements elicitation, software design, implementation, testing, and maintenance. It also covers topics such as project management, software process, and software quality."
        );

        Long nusModId1 = nUSModuleSessionBean.createNUSModule(
                nusModule1,
                facultyId3
        );
        Long nusModId2 = nUSModuleSessionBean.createNUSModule(
                nusModule2,
                facultyId3
        );
        Long nusModId3 = nUSModuleSessionBean.createNUSModule(
                nusModule3,
                facultyId3
        );
        Long nusModId4 = nUSModuleSessionBean.createNUSModule(
                nusModule4,
                facultyId3
        );
        Long nusModId5 = nUSModuleSessionBean.createNUSModule(
                nusModule5,
                facultyId3
        );
        Long nusModId6 = nUSModuleSessionBean.createNUSModule(
                nusModule6,
                facultyId3
        );
        Long nusModId7 = nUSModuleSessionBean.createNUSModule(
                nusModule7,
                facultyId3
        );
        Long nusModId8 = nUSModuleSessionBean.createNUSModule(
                nusModule8,
                facultyId3
        );
        Long nusModId9 = nUSModuleSessionBean.createNUSModule(
                nusModule9,
                facultyId3
        );
        Long nusModId10 = nUSModuleSessionBean.createNUSModule(
                nusModule10,
                facultyId3
        );

        nUSModuleSessionBean.createNUSModule(nusModule1, facultyId3);
        nUSModuleSessionBean.createNUSModule(nusModule2, facultyId3);
        nUSModuleSessionBean.createNUSModule(nusModule3, facultyId3);
        nUSModuleSessionBean.createNUSModule(nusModule4, facultyId3);
        nUSModuleSessionBean.createNUSModule(nusModule5, facultyId3);
        nUSModuleSessionBean.createNUSModule(nusModule6, facultyId3);
        nUSModuleSessionBean.createNUSModule(nusModule7, facultyId3);
        nUSModuleSessionBean.createNUSModule(nusModule8, facultyId3);
        nUSModuleSessionBean.createNUSModule(nusModule9, facultyId3);
        nUSModuleSessionBean.createNUSModule(nusModule10, facultyId3);

        NUSModule nusModule11 = new NUSModule(
                "Introduction to Political Science",
                "PS1101E",
                "This module introduces students to the study of politics and political science. It covers topics such as political ideologies, political systems and institutions, and political processes and behavior."
        );
        NUSModule nusModule12 = new NUSModule(
                "Understanding Southeast Asia",
                "SE1101E",
                "This module provides an overview of the social, cultural, economic, and political dimensions of Southeast Asia. It covers topics such as the history and diversity of the region, its relations with the world, and contemporary issues and challenges."
        );
        NUSModule nusModule13 = new NUSModule(
                "Introduction to Sociology",
                "SC1101E",
                "This module introduces students to the study of sociology and sociological theories. It covers topics such as social structures, socialization and identity, social inequality and stratification, and social change."
        );
        NUSModule nusModule14 = new NUSModule(
                "Introduction to Psychology",
                "PSY1101E",
                "This module provides an overview of the field of psychology and the scientific study of behavior and mental processes. It covers topics such as biological bases of behavior, perception, learning and memory, and social cognition and attitudes."
        );
        NUSModule nusModule15 = new NUSModule(
                "World History: An Introduction",
                "HY1101E",
                "This module offers an introduction to world history from pre-modern times to the present. It covers key events and processes that have shaped human societies and cultures, and highlights their global and comparative dimensions."
        );
        NUSModule nusModule16 = new NUSModule(
                "Introduction to Literature in English",
                "EN1101E",
                "This module introduces students to the study of literature in English, and aims to develop critical reading and analytical skills. It covers different literary genres and forms, and examines the cultural and historical contexts of literary works."
        );
        NUSModule nusModule17 = new NUSModule(
                "Introduction to Theatre Studies",
                "TS1101E",
                "This module introduces students to the study of theatre and performance, and aims to develop critical and creative thinking skills. It covers different theatrical forms and practices, and examines their social, cultural, and political contexts."
        );
        NUSModule nusModule18 = new NUSModule(
                "Introduction to Philosophy",
                "PL1101E",
                "This module introduces students to the study of philosophy and its central questions and debates. It covers topics such as the nature of reality, knowledge and skepticism, ethics and morality, and the meaning of life."
        );
        NUSModule nusModule19 = new NUSModule(
                "Introduction to Southeast Asian Studies",
                "SSA1201E",
                "This module provides an interdisciplinary introduction to the study of Southeast Asia, and aims to develop critical thinking and research skills. It covers topics such as the region's history, society and culture, politics and governance, and economic development."
        );
        NUSModule nusModule20 = new NUSModule(
                "Introduction to Communications and New Media",
                "NM1101E",
                "This module introduces students to the study of communications and new media, and examines their social, cultural, and political dimensions. It covers topics such as media technologies, digital cultures, and communication theories and practices."
        );

        Long nusModId11 = nUSModuleSessionBean.createNUSModule(
                nusModule11,
                facultyId1
        );
        Long nusModId12 = nUSModuleSessionBean.createNUSModule(
                nusModule12,
                facultyId1
        );
        Long nusModId13 = nUSModuleSessionBean.createNUSModule(
                nusModule13,
                facultyId1
        );
        Long nusModId14 = nUSModuleSessionBean.createNUSModule(
                nusModule14,
                facultyId1
        );
        Long nusModId15 = nUSModuleSessionBean.createNUSModule(
                nusModule15,
                facultyId1
        );
        Long nusModId16 = nUSModuleSessionBean.createNUSModule(
                nusModule16,
                facultyId1
        );
        Long nusModId17 = nUSModuleSessionBean.createNUSModule(
                nusModule17,
                facultyId1
        );
        Long nusModId18 = nUSModuleSessionBean.createNUSModule(
                nusModule18,
                facultyId1
        );
        Long nusModId19 = nUSModuleSessionBean.createNUSModule(
                nusModule19,
                facultyId1
        );
        Long nusModId20 = nUSModuleSessionBean.createNUSModule(
                nusModule20,
                facultyId1
        );

        nUSModuleSessionBean.createNUSModule(nusModule11, facultyId1);
        nUSModuleSessionBean.createNUSModule(nusModule12, facultyId1);
        nUSModuleSessionBean.createNUSModule(nusModule13, facultyId1);
        nUSModuleSessionBean.createNUSModule(nusModule14, facultyId1);
        nUSModuleSessionBean.createNUSModule(nusModule15, facultyId1);
        nUSModuleSessionBean.createNUSModule(nusModule16, facultyId1);
        nUSModuleSessionBean.createNUSModule(nusModule17, facultyId1);
        nUSModuleSessionBean.createNUSModule(nusModule18, facultyId1);
        nUSModuleSessionBean.createNUSModule(nusModule19, facultyId1);
        nUSModuleSessionBean.createNUSModule(nusModule20, facultyId1);

        NUSModule nusModule21 = new NUSModule(
                "Introduction to Accounting",
                "ACC1002",
                "This module provides an introduction to financial accounting concepts and principles. It covers topics such as the accounting equation, financial statements, accounting cycles, and accounting systems."
        );
        NUSModule nusModule22 = new NUSModule(
                "Introduction to Marketing",
                "MKT1003",
                "This module introduces students to the fundamental concepts and principles of marketing. It covers topics such as market segmentation, product development, pricing strategies, promotional mix, and consumer behavior."
        );
        NUSModule nusModule23 = new NUSModule(
                "Introduction to Finance",
                "FIN1002",
                "This module provides an introduction to financial management concepts and principles. It covers topics such as time value of money, risk and return, capital budgeting, financial markets, and financial instruments."
        );
        NUSModule nusModule24 = new NUSModule(
                "Business Law",
                "BSP1004",
                "This module introduces students to the legal aspects of business operations. It covers topics such as contracts, torts, intellectual property, business organizations, and international business law."
        );
        NUSModule nusModule25 = new NUSModule(
                "Operations Management",
                "OMG1005",
                "This module introduces students to the principles and practices of operations management. It covers topics such as process design, capacity planning, inventory management, quality control, and supply chain management."
        );
        NUSModule nusModule26 = new NUSModule(
                "Business Analytics",
                "DSA2102",
                "This module provides an introduction to data analytics and its applications in business decision-making. It covers topics such as data visualization, statistical inference, regression analysis, and predictive modeling."
        );
        NUSModule nusModule27 = new NUSModule(
                "Strategic Management",
                "BSP3001",
                "This module provides an overview of strategic management concepts and frameworks. It covers topics such as strategic analysis, strategy formulation, implementation, and evaluation, and examines the role of organizational culture and leadership in strategic decision-making."
        );
        NUSModule nusModule28 = new NUSModule(
                "Entrepreneurship",
                "BSP3007",
                "This module introduces students to the principles and practices of entrepreneurship. It covers topics such as idea generation, business planning, funding and financing, marketing and sales, and managing growth and expansion."
        );
        NUSModule nusModule29 = new NUSModule(
                "International Business",
                "BSP3513",
                "This module provides an overview of international business operations and strategies. It covers topics such as international trade theories, foreign direct investment, global business environment, cross-cultural management, and international marketing."
        );
        NUSModule nusModule30 = new NUSModule(
                "Corporate Social Responsibility",
                "BSP3514",
                "This module examines the concept of corporate social responsibility and its implications for business operations and strategies. It covers topics such as ethical decision-making, stakeholder engagement, sustainability reporting, and social impact assessment."
        );

        Long nusModId21 = nUSModuleSessionBean.createNUSModule(
                nusModule21,
                facultyId2
        );
        Long nusModId22 = nUSModuleSessionBean.createNUSModule(
                nusModule22,
                facultyId2
        );
        Long nusModId23 = nUSModuleSessionBean.createNUSModule(
                nusModule23,
                facultyId2
        );
        Long nusModId24 = nUSModuleSessionBean.createNUSModule(
                nusModule24,
                facultyId2
        );
        Long nusModId25 = nUSModuleSessionBean.createNUSModule(
                nusModule25,
                facultyId2
        );
        Long nusModId26 = nUSModuleSessionBean.createNUSModule(
                nusModule26,
                facultyId2
        );
        Long nusModId27 = nUSModuleSessionBean.createNUSModule(
                nusModule27,
                facultyId2
        );
        Long nusModId28 = nUSModuleSessionBean.createNUSModule(
                nusModule28,
                facultyId2
        );
        Long nusModId29 = nUSModuleSessionBean.createNUSModule(
                nusModule29,
                facultyId2
        );
        Long nusModId30 = nUSModuleSessionBean.createNUSModule(
                nusModule30,
                facultyId2
        );

        nUSModuleSessionBean.createNUSModule(nusModule21, facultyId2);
        nUSModuleSessionBean.createNUSModule(nusModule22, facultyId2);
        nUSModuleSessionBean.createNUSModule(nusModule23, facultyId2);
        nUSModuleSessionBean.createNUSModule(nusModule24, facultyId2);
        nUSModuleSessionBean.createNUSModule(nusModule25, facultyId2);
        nUSModuleSessionBean.createNUSModule(nusModule26, facultyId2);
        nUSModuleSessionBean.createNUSModule(nusModule27, facultyId2);
        nUSModuleSessionBean.createNUSModule(nusModule28, facultyId2);
        nUSModuleSessionBean.createNUSModule(nusModule29, facultyId2);
        nUSModuleSessionBean.createNUSModule(nusModule30, facultyId2);

        NUSModule nusModule31 = new NUSModule(
                "Oral Biology",
                "DEN1101",
                "This module introduces students to the biological principles and processes related to the oral cavity. It covers topics such as oral anatomy, histology, embryology, and microbiology."
        );
        NUSModule nusModule32 = new NUSModule(
                "Dental Materials",
                "DEN1201",
                "This module provides an overview of the materials commonly used in dentistry, and their properties, selection criteria, and clinical applications. It covers topics such as restorative materials, impression materials, and dental ceramics."
        );
        NUSModule nusModule33 = new NUSModule(
                "Oral Health Promotion",
                "DEN1301",
                "This module examines the principles and strategies for promoting oral health and preventing oral diseases at the individual and community levels. It covers topics such as health education, behavior change, and social determinants of oral health."
        );
        NUSModule nusModule34 = new NUSModule(
                "Dental Anatomy and Occlusion",
                "DEN2101",
                "This module provides an in-depth study of dental anatomy and occlusion, and their clinical implications for restorative and prosthodontic treatment planning. It covers topics such as tooth morphology, occlusal relationships, and functional anatomy."
        );
        NUSModule nusModule35 = new NUSModule(
                "Clinical Periodontology",
                "DEN2201",
                "This module focuses on the diagnosis, treatment, and prevention of periodontal diseases, and their impact on oral and systemic health. It covers topics such as periodontal anatomy and physiology, periodontal assessment, and nonsurgical and surgical therapy."
        );
        NUSModule nusModule36 = new NUSModule(
                "Oral and Maxillofacial Surgery",
                "DEN2301",
                "This module provides an introduction to the principles and practices of oral and maxillofacial surgery, and their clinical applications for the management of oral and facial conditions. It covers topics such as surgical anatomy, anesthesia, and surgical techniques."
        );
        NUSModule nusModule37 = new NUSModule(
                "Clinical Endodontics",
                "DEN3101",
                "This module focuses on the diagnosis, treatment, and prevention of pulpal and periapical diseases, and their management using endodontic techniques. It covers topics such as pulp biology, root canal anatomy, and endodontic instrumentation and obturation."
        );
        NUSModule nusModule38 = new NUSModule(
                "Orthodontics",
                "DEN3201",
                "This module introduces students to the principles and practices of orthodontics, and their clinical applications for the correction of malocclusions and dentofacial deformities. It covers topics such as tooth movement, growth and development, and orthodontic appliances."
        );
        NUSModule nusModule39 = new NUSModule(
                "Prosthodontics",
                "DEN3301",
                "This module provides an overview of the principles and practices of prosthodontics, and their clinical applications for the rehabilitation of oral function and aesthetics. It covers topics such as dental materials, fixed and removable prosthodontics, and implant dentistry."
        );

        Long nusModId31 = nUSModuleSessionBean.createNUSModule(
                nusModule31,
                facultyId4
        );
        Long nusModId32 = nUSModuleSessionBean.createNUSModule(
                nusModule32,
                facultyId4
        );
        Long nusModId33 = nUSModuleSessionBean.createNUSModule(
                nusModule33,
                facultyId4
        );
        Long nusModId34 = nUSModuleSessionBean.createNUSModule(
                nusModule34,
                facultyId4
        );
        Long nusModId35 = nUSModuleSessionBean.createNUSModule(
                nusModule35,
                facultyId4
        );
        Long nusModId36 = nUSModuleSessionBean.createNUSModule(
                nusModule36,
                facultyId4
        );
        Long nusModId37 = nUSModuleSessionBean.createNUSModule(
                nusModule37,
                facultyId4
        );
        Long nusModId38 = nUSModuleSessionBean.createNUSModule(
                nusModule38,
                facultyId4
        );
        Long nusModId39 = nUSModuleSessionBean.createNUSModule(
                nusModule39,
                facultyId4
        );

        nUSModuleSessionBean.createNUSModule(nusModule31, facultyId4);
        nUSModuleSessionBean.createNUSModule(nusModule32, facultyId4);
        nUSModuleSessionBean.createNUSModule(nusModule33, facultyId4);
        nUSModuleSessionBean.createNUSModule(nusModule34, facultyId4);
        nUSModuleSessionBean.createNUSModule(nusModule35, facultyId4);
        nUSModuleSessionBean.createNUSModule(nusModule36, facultyId4);
        nUSModuleSessionBean.createNUSModule(nusModule37, facultyId4);
        nUSModuleSessionBean.createNUSModule(nusModule38, facultyId4);
        nUSModuleSessionBean.createNUSModule(nusModule39, facultyId4);

        NUSModule nusModule40 = new NUSModule(
                "Introduction to Environmental Systems and Sustainable Development",
                "ESD1101",
                "This module introduces students to the concepts and principles of environmental systems and sustainable development. It covers topics such as ecosystems and biodiversity, climate change, energy and resources, and sustainable urban development."
        );
        NUSModule nusModule41 = new NUSModule(
                "Building Construction",
                "BC1101",
                "This module introduces students to the principles and practices of building construction. It covers topics such as building materials and systems, construction processes and techniques, and building regulations and codes."
        );
        NUSModule nusModule42 = new NUSModule(
                "Architectural Design",
                "AR1101",
                "This module introduces students to the fundamentals of architectural design. It covers topics such as design principles and processes, site analysis and planning, and architectural representation and communication."
        );
        NUSModule nusModule43 = new NUSModule(
                "Landscape Architecture",
                "LA1101",
                "This module introduces students to the theory and practice of landscape architecture. It covers topics such as site analysis and planning, landscape design principles and processes, and sustainable landscape management."
        );
        NUSModule nusModule44 = new NUSModule(
                "Urban Planning and Design",
                "UP1101",
                "This module introduces students to the theory and practice of urban planning and design. It covers topics such as urbanization and urban systems, planning principles and processes, and the design of urban spaces and infrastructure."
        );
        NUSModule nusModule45 = new NUSModule(
                "Industrial Design",
                "ID1101",
                "This module introduces students to the theory and practice of industrial design. It covers topics such as design thinking and methodology, product design and development, and manufacturing processes and technologies."
        );
        NUSModule nusModule46 = new NUSModule(
                "Interior Design",
                "ID1201",
                "This module introduces students to the theory and practice of interior design. It covers topics such as spatial design principles and processes, materials and finishes, and lighting and environmental systems."
        );
        NUSModule nusModule47 = new NUSModule(
                "Design Communication",
                "DC1101",
                "This module introduces students to the principles and techniques of design communication. It covers topics such as visual communication, digital media and tools, and design presentation and critique."
        );
        NUSModule nusModule48 = new NUSModule(
                "Construction and Project Management",
                "CPD1101",
                "This module introduces students to the principles and practices of construction and project management. It covers topics such as project planning and scheduling, cost estimation and control, and quality assurance and safety."
        );
        NUSModule nusModule49 = new NUSModule(
                "Real Estate and Urban Economics",
                "RE1701",
                "This module introduces students to the theory and practice of real estate and urban economics. It covers topics such as real estate markets and valuation, land use and development, and urban economic policy and planning."
        );

        Long nusModId40 = nUSModuleSessionBean.createNUSModule(
                nusModule40,
                facultyId5
        );
        Long nusModId41 = nUSModuleSessionBean.createNUSModule(
                nusModule41,
                facultyId5
        );
        Long nusModId42 = nUSModuleSessionBean.createNUSModule(
                nusModule42,
                facultyId5
        );
        Long nusModId43 = nUSModuleSessionBean.createNUSModule(
                nusModule43,
                facultyId5
        );
        Long nusModId44 = nUSModuleSessionBean.createNUSModule(
                nusModule44,
                facultyId5
        );
        Long nusModId45 = nUSModuleSessionBean.createNUSModule(
                nusModule45,
                facultyId5
        );
        Long nusModId46 = nUSModuleSessionBean.createNUSModule(
                nusModule46,
                facultyId5
        );
        Long nusModId47 = nUSModuleSessionBean.createNUSModule(
                nusModule47,
                facultyId5
        );
        Long nusModId48 = nUSModuleSessionBean.createNUSModule(
                nusModule48,
                facultyId5
        );
        Long nusModId49 = nUSModuleSessionBean.createNUSModule(
                nusModule49,
                facultyId5
        );

        nUSModuleSessionBean.createNUSModule(nusModule40, facultyId5);
        nUSModuleSessionBean.createNUSModule(nusModule41, facultyId5);
        nUSModuleSessionBean.createNUSModule(nusModule42, facultyId5);
        nUSModuleSessionBean.createNUSModule(nusModule43, facultyId5);
        nUSModuleSessionBean.createNUSModule(nusModule44, facultyId5);
        nUSModuleSessionBean.createNUSModule(nusModule45, facultyId5);
        nUSModuleSessionBean.createNUSModule(nusModule46, facultyId5);
        nUSModuleSessionBean.createNUSModule(nusModule47, facultyId5);
        nUSModuleSessionBean.createNUSModule(nusModule48, facultyId5);
        nUSModuleSessionBean.createNUSModule(nusModule49, facultyId5);

        NUSModule nusModule50 = new NUSModule(
                "Introduction to Engineering",
                "EG1108",
                "This module provides an overview of the various disciplines of engineering and their applications in the real world. It covers topics such as the engineering design process, basic engineering principles, and case studies of engineering projects."
        );
        NUSModule nusModule51 = new NUSModule(
                "Introduction to Computer Science",
                "CS1010",
                "This module introduces students to the fundamental concepts of computer science, including programming, algorithms, and data structures. It also covers software engineering principles and basic computer architecture."
        );
        NUSModule nusModule52 = new NUSModule(
                "Introduction to Electrical and Electronic Engineering",
                "EE1001X",
                "This module provides an introduction to the principles of electrical and electronic engineering. It covers topics such as electric circuits, electronics, and digital systems."
        );
        NUSModule nusModule53 = new NUSModule(
                "Introduction to Materials Science and Engineering",
                "EG1413",
                "This module introduces the basic principles of materials science and engineering. It covers topics such as the structure and properties of materials, phase diagrams, and materials selection."
        );
        NUSModule nusModule54 = new NUSModule(
                "Introduction to Biomedical Engineering",
                "BME101",
                "This module provides an overview of the interdisciplinary field of biomedical engineering. It covers topics such as medical imaging, biomechanics, and biomaterials."
        );
        NUSModule nusModule55 = new NUSModule(
                "Introduction to Chemical Engineering",
                "EG1109",
                "This module introduces the fundamental principles of chemical engineering. It covers topics such as material and energy balances, thermodynamics, and transport phenomena."
        );
        NUSModule nusModule56 = new NUSModule(
                "Introduction to Environmental Engineering",
                "EG1401",
                "This module provides an introduction to the principles of environmental engineering. It covers topics such as water and wastewater treatment, air pollution control, and solid waste management."
        );
        NUSModule nusModule57 = new NUSModule(
                "Introduction to Mechanical Engineering",
                "ME1101E",
                "This module introduces the fundamental principles of mechanical engineering. It covers topics such as statics, dynamics, and mechanics of materials."
        );
        NUSModule nusModule58 = new NUSModule(
                "Introduction to Aerospace Engineering",
                "ME2114",
                "This module provides an overview of the principles of aerospace engineering. It covers topics such as aerodynamics, aircraft structures, and propulsion systems."
        );
        NUSModule nusModule59 = new NUSModule(
                "Introduction to Engineering Mathematics",
                "MA1505",
                "This module introduces the mathematical tools and concepts used in engineering. It covers topics such as calculus, linear algebra, and differential equations."
        );

        Long nusModId50 = nUSModuleSessionBean.createNUSModule(
                nusModule50,
                facultyId6
        );
        Long nusModId51 = nUSModuleSessionBean.createNUSModule(
                nusModule51,
                facultyId6
        );
        Long nusModId52 = nUSModuleSessionBean.createNUSModule(
                nusModule52,
                facultyId6
        );
        Long nusModId53 = nUSModuleSessionBean.createNUSModule(
                nusModule53,
                facultyId6
        );
        Long nusModId54 = nUSModuleSessionBean.createNUSModule(
                nusModule54,
                facultyId6
        );
        Long nusModId55 = nUSModuleSessionBean.createNUSModule(
                nusModule55,
                facultyId6
        );
        Long nusModId56 = nUSModuleSessionBean.createNUSModule(
                nusModule56,
                facultyId6
        );
        Long nusModId57 = nUSModuleSessionBean.createNUSModule(
                nusModule57,
                facultyId6
        );
        Long nusModId58 = nUSModuleSessionBean.createNUSModule(
                nusModule58,
                facultyId6
        );
        Long nusModId59 = nUSModuleSessionBean.createNUSModule(
                nusModule59,
                facultyId6
        );

        nUSModuleSessionBean.createNUSModule(nusModule50, facultyId6);
        nUSModuleSessionBean.createNUSModule(nusModule51, facultyId6);
        nUSModuleSessionBean.createNUSModule(nusModule52, facultyId6);
        nUSModuleSessionBean.createNUSModule(nusModule53, facultyId6);
        nUSModuleSessionBean.createNUSModule(nusModule54, facultyId6);
        nUSModuleSessionBean.createNUSModule(nusModule55, facultyId6);
        nUSModuleSessionBean.createNUSModule(nusModule56, facultyId6);
        nUSModuleSessionBean.createNUSModule(nusModule57, facultyId6);
        nUSModuleSessionBean.createNUSModule(nusModule58, facultyId6);
        nUSModuleSessionBean.createNUSModule(nusModule59, facultyId6);

        NUSModule nusModule60 = new NUSModule(
                "Introduction to Law",
                "LAW1101",
                "This module introduces students to the study of law and legal systems. It covers topics such as the sources of law, legal reasoning, and the administration of justice."
        );
        NUSModule nusModule61 = new NUSModule(
                "Criminal Law",
                "LAW2201",
                "This module provides an introduction to the principles and concepts of criminal law. It covers topics such as the elements of criminal offenses, defenses, and sentencing."
        );
        NUSModule nusModule62 = new NUSModule(
                "Contract Law",
                "LAW2202",
                "This module provides an introduction to the principles and concepts of contract law. It covers topics such as offer and acceptance, consideration, and breach of contract."
        );
        NUSModule nusModule63 = new NUSModule(
                "Tort Law",
                "LAW2203",
                "This module provides an introduction to the principles and concepts of tort law. It covers topics such as negligence, nuisance, and strict liability."
        );
        NUSModule nusModule64 = new NUSModule(
                "Property Law",
                "LAW2204",
                "This module provides an introduction to the principles and concepts of property law. It covers topics such as estates and interests, trusts, and real property transactions."
        );
        NUSModule nusModule65 = new NUSModule(
                "Constitutional Law",
                "LAW3201",
                "This module provides an introduction to the principles and concepts of constitutional law. It covers topics such as the sources and structure of the constitution, and the powers of the executive, legislature, and judiciary."
        );
        NUSModule nusModule66 = new NUSModule(
                "Administrative Law",
                "LAW3202",
                "This module provides an introduction to the principles and concepts of administrative law. It covers topics such as judicial review, administrative tribunals, and the rule of law."
        );
        NUSModule nusModule67 = new NUSModule(
                "International Law",
                "LAW3203",
                "This module provides an introduction to the principles and concepts of international law. It covers topics such as the sources and subjects of international law, the law of treaties, and the settlement of disputes."
        );
        NUSModule nusModule68 = new NUSModule(
                "Commercial Law",
                "LAW3204",
                "This module provides an introduction to the principles and concepts of commercial law. It covers topics such as sales, agency, and the law of business organizations."
        );
        NUSModule nusModule69 = new NUSModule(
                "Family Law",
                "LAW3205",
                "This module provides an introduction to the principles and concepts of family law. It covers topics such as marriage and divorce, child custody, and the legal rights and obligations of family members."
        );

        Long nusModId60 = nUSModuleSessionBean.createNUSModule(
                nusModule60,
                facultyId7
        );
        Long nusModId61 = nUSModuleSessionBean.createNUSModule(
                nusModule61,
                facultyId7
        );
        Long nusModId62 = nUSModuleSessionBean.createNUSModule(
                nusModule62,
                facultyId7
        );
        Long nusModId63 = nUSModuleSessionBean.createNUSModule(
                nusModule63,
                facultyId7
        );
        Long nusModId64 = nUSModuleSessionBean.createNUSModule(
                nusModule64,
                facultyId7
        );
        Long nusModId65 = nUSModuleSessionBean.createNUSModule(
                nusModule65,
                facultyId7
        );
        Long nusModId66 = nUSModuleSessionBean.createNUSModule(
                nusModule66,
                facultyId7
        );
        Long nusModId67 = nUSModuleSessionBean.createNUSModule(
                nusModule67,
                facultyId7
        );
        Long nusModId68 = nUSModuleSessionBean.createNUSModule(
                nusModule68,
                facultyId7
        );
        Long nusModId69 = nUSModuleSessionBean.createNUSModule(
                nusModule69,
                facultyId7
        );

        nUSModuleSessionBean.createNUSModule(nusModule60, facultyId7);
        nUSModuleSessionBean.createNUSModule(nusModule61, facultyId7);
        nUSModuleSessionBean.createNUSModule(nusModule62, facultyId7);
        nUSModuleSessionBean.createNUSModule(nusModule63, facultyId7);
        nUSModuleSessionBean.createNUSModule(nusModule64, facultyId7);
        nUSModuleSessionBean.createNUSModule(nusModule65, facultyId7);
        nUSModuleSessionBean.createNUSModule(nusModule66, facultyId7);
        nUSModuleSessionBean.createNUSModule(nusModule67, facultyId7);
        nUSModuleSessionBean.createNUSModule(nusModule68, facultyId7);
        nUSModuleSessionBean.createNUSModule(nusModule69, facultyId7);

        NUSModule nusModule70 = new NUSModule(
                "Anatomy",
                "MDP1101",
                "This module provides an overview of the human body's structure, including its organs, tissues, and systems."
        );
        NUSModule nusModule71 = new NUSModule(
                "Physiology",
                "MDP1102",
                "This module covers the functions and mechanisms of the human body's cells, organs, and systems."
        );
        NUSModule nusModule72 = new NUSModule(
                "Pharmacology",
                "MDP2101",
                "This module introduces students to the principles and mechanisms of drug actions in the human body."
        );
        NUSModule nusModule73 = new NUSModule(
                "Pathology",
                "MDP2102",
                "This module covers the study of the nature, causes, and development of diseases in the human body."
        );
        NUSModule nusModule74 = new NUSModule(
                "Medical Ethics and Law",
                "MDP3101",
                "This module explores the ethical and legal issues related to medical practice, including patient rights, confidentiality, and end-of-life care."
        );
        NUSModule nusModule75 = new NUSModule(
                "Public Health",
                "MDP3102",
                "This module covers the principles and practice of public health, including disease prevention, health promotion, and health policy."
        );
        NUSModule nusModule76 = new NUSModule(
                "Human Immunology",
                "MDP3103",
                "This module provides an in-depth understanding of the human immune system and its role in health and disease."
        );
        NUSModule nusModule77 = new NUSModule(
                "Clinical Pharmacology and Therapeutics",
                "MDP4101",
                "This module covers the principles and practice of drug therapy in clinical settings, including drug interactions, adverse effects, and patient monitoring."
        );
        NUSModule nusModule78 = new NUSModule(
                "Clinical Skills",
                "MDP4102",
                "This module focuses on developing practical clinical skills, including physical examination, diagnostic reasoning, and communication with patients."
        );
        NUSModule nusModule79 = new NUSModule(
                "Medical Research Methods",
                "MDP4103",
                "This module introduces students to the basic principles and methods of medical research, including study design, data analysis, and ethical considerations."
        );

        Long nusModId70 = nUSModuleSessionBean.createNUSModule(
                nusModule70,
                facultyId8
        );
        Long nusModId71 = nUSModuleSessionBean.createNUSModule(
                nusModule71,
                facultyId8
        );
        Long nusModId72 = nUSModuleSessionBean.createNUSModule(
                nusModule72,
                facultyId8
        );
        Long nusModId73 = nUSModuleSessionBean.createNUSModule(
                nusModule73,
                facultyId8
        );
        Long nusModId74 = nUSModuleSessionBean.createNUSModule(
                nusModule74,
                facultyId8
        );
        Long nusModId75 = nUSModuleSessionBean.createNUSModule(
                nusModule75,
                facultyId8
        );
        Long nusModId76 = nUSModuleSessionBean.createNUSModule(
                nusModule76,
                facultyId8
        );
        Long nusModId77 = nUSModuleSessionBean.createNUSModule(
                nusModule77,
                facultyId8
        );
        Long nusModId78 = nUSModuleSessionBean.createNUSModule(
                nusModule78,
                facultyId8
        );
        Long nusModId79 = nUSModuleSessionBean.createNUSModule(
                nusModule79,
                facultyId8
        );

        nUSModuleSessionBean.createNUSModule(nusModule70, facultyId8);
        nUSModuleSessionBean.createNUSModule(nusModule71, facultyId8);
        nUSModuleSessionBean.createNUSModule(nusModule72, facultyId8);
        nUSModuleSessionBean.createNUSModule(nusModule73, facultyId8);
        nUSModuleSessionBean.createNUSModule(nusModule74, facultyId8);
        nUSModuleSessionBean.createNUSModule(nusModule75, facultyId8);
        nUSModuleSessionBean.createNUSModule(nusModule76, facultyId8);
        nUSModuleSessionBean.createNUSModule(nusModule77, facultyId8);
        nUSModuleSessionBean.createNUSModule(nusModule78, facultyId8);
        nUSModuleSessionBean.createNUSModule(nusModule79, facultyId8);

        NUSModule nusModule80 = new NUSModule(
                "Chemistry for Life Sciences",
                "CM1121",
                "This module provides an introduction to the principles and applications of chemistry in the context of life sciences. Topics covered include atomic and molecular structure, chemical bonding, thermodynamics, and organic chemistry."
        );
        NUSModule nusModule81 = new NUSModule(
                "Mathematics I",
                "MA1100",
                "This module provides an introduction to calculus and linear algebra. Topics covered include limits, derivatives, integrals, differential equations, matrices, and systems of linear equations."
        );
        NUSModule nusModule82 = new NUSModule(
                "Mathematics II",
                "MA1101R",
                "This module builds on the concepts and techniques introduced in Mathematics I. Topics covered include multivariable calculus, vector calculus, and Fourier series and transforms."
        );
        NUSModule nusModule83 = new NUSModule(
                "Physics I",
                "PC1141",
                "This module provides an introduction to classical mechanics, waves, and thermodynamics. Topics covered include kinematics, forces, energy, momentum, oscillations, waves, and thermodynamics."
        );
        NUSModule nusModule84 = new NUSModule(
                "Physics II",
                "PC1142",
                "This module builds on the concepts and techniques introduced in Physics I. Topics covered include electromagnetism, optics, and modern physics."
        );
        NUSModule nusModule85 = new NUSModule(
                "Biology: Basic Principles and Techniques",
                "LSM1102",
                "This module provides an introduction to the basic principles and techniques of biology. Topics covered include the structure and function of cells, biomolecules, genetics, and evolution."
        );
        NUSModule nusModule86 = new NUSModule(
                "Fundamentals of Materials Science and Engineering",
                "EG1311",
                "This module provides an introduction to the structure, properties, and processing of materials. Topics covered include crystal structure, defects, phase diagrams, mechanical properties, and thermal properties."
        );
        NUSModule nusModule87 = new NUSModule(
                "Introduction to Data Science",
                "DSA1101",
                "This module provides an introduction to the principles and techniques of data science. Topics covered include data manipulation, data visualization, statistical inference, and machine learning."
        );
        NUSModule nusModule88 = new NUSModule(
                "Environmental Science",
                "GES1002",
                "This module provides an introduction to the scientific study of the environment. Topics covered include the earth's atmosphere, hydrosphere, and lithosphere, and the interactions between human activities and the environment."
        );
        NUSModule nusModule89 = new NUSModule(
                "Introduction to Computational Thinking",
                "CS1010S",
                "This module provides an introduction to computational thinking and programming. Topics covered include problem-solving strategies, algorithms, data structures, and programming in a high-level language."
        );

        Long nusModId80 = nUSModuleSessionBean.createNUSModule(
                nusModule80,
                facultyId9
        );
        Long nusModId81 = nUSModuleSessionBean.createNUSModule(
                nusModule81,
                facultyId9
        );
        Long nusModId82 = nUSModuleSessionBean.createNUSModule(
                nusModule82,
                facultyId9
        );
        Long nusModId83 = nUSModuleSessionBean.createNUSModule(
                nusModule83,
                facultyId9
        );
        Long nusModId84 = nUSModuleSessionBean.createNUSModule(
                nusModule84,
                facultyId9
        );
        Long nusModId85 = nUSModuleSessionBean.createNUSModule(
                nusModule85,
                facultyId9
        );
        Long nusModId86 = nUSModuleSessionBean.createNUSModule(
                nusModule86,
                facultyId9
        );
        Long nusModId87 = nUSModuleSessionBean.createNUSModule(
                nusModule87,
                facultyId9
        );
        Long nusModId88 = nUSModuleSessionBean.createNUSModule(
                nusModule88,
                facultyId9
        );
        Long nusModId89 = nUSModuleSessionBean.createNUSModule(
                nusModule89,
                facultyId9
        );

        nUSModuleSessionBean.createNUSModule(nusModule80, facultyId9);
        nUSModuleSessionBean.createNUSModule(nusModule81, facultyId9);
        nUSModuleSessionBean.createNUSModule(nusModule82, facultyId9);
        nUSModuleSessionBean.createNUSModule(nusModule83, facultyId9);
        nUSModuleSessionBean.createNUSModule(nusModule84, facultyId9);
        nUSModuleSessionBean.createNUSModule(nusModule85, facultyId9);
        nUSModuleSessionBean.createNUSModule(nusModule86, facultyId9);
        nUSModuleSessionBean.createNUSModule(nusModule87, facultyId9);
        nUSModuleSessionBean.createNUSModule(nusModule88, facultyId9);
        nUSModuleSessionBean.createNUSModule(nusModule89, facultyId9);

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
        Country country6 = new Country("Switzerland", region2);
        Country country7 = new Country("Hong Kong", region3);
        Country country8 = new Country("China", region3);
        Country country9 = new Country("South Africa", region1);
        Country country10 = new Country("Egypt", region1);
        Country country11 = new Country("Greece", region1);
        Country country12 = new Country("Korea", region3);
        Country country13 = new Country("Malaysia", region3);
        Country country14 = new Country("Germany", region2);
        Country country15 = new Country("Netherlands", region2);

        Long cId1 = countrySessionBean.createNewCountry(country1);
        Long cId2 = countrySessionBean.createNewCountry(country2);
        Long cId3 = countrySessionBean.createNewCountry(country3);
        Long cId4 = countrySessionBean.createNewCountry(country4);
        Long cId5 = countrySessionBean.createNewCountry(country5);
        Long cId6 = countrySessionBean.createNewCountry(country6);
        Long cId7 = countrySessionBean.createNewCountry(country7);
        Long cId8 = countrySessionBean.createNewCountry(country8);
        Long cId9 = countrySessionBean.createNewCountry(country9);
        Long cId10 = countrySessionBean.createNewCountry(country10);
        Long cId11 = countrySessionBean.createNewCountry(country11);
        Long cId12 = countrySessionBean.createNewCountry(country12);
        Long cId13 = countrySessionBean.createNewCountry(country13);
        Long cId14 = countrySessionBean.createNewCountry(country14);
        Long cId15 = countrySessionBean.createNewCountry(country15);

        PU pu1 = new PU(
                "Africa University",
                "Africa University is a private, Pan-African and United Methodist-related institution. It has over 1,200 students from 36 African countries.[1] It is located 17 km northwest of Mutare, Zimbabwe. It grants bachelor's, master's and PhD degrees in various programs.",
                "data:image/png;base64,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"
        );
        PU pu2 = new PU(
                "Oxford University",
                "The University of Oxford is a collegiate research university in Oxford, England. There is evidence of teaching as early as 1096, making it the oldest university in the English-speaking world and the world's second-oldest university in continuous operation.",
                "https://yt3.googleusercontent.com/ytc/AL5GRJVd3R5_bH1D-OecPoAv_pKMIX_EZ0ELk2ui53ZHbQ=s900-c-k-c0x00ffffff-no-rj"
        );
        PU pu3 = new PU(
                "University of Tokyo",
                "The University of Tokyo (東京大学, Tōkyō daigaku; Todai or UTokyo) is a public research university in Bunkyō, Tokyo, Japan. Established in 1877, the university was the first Imperial University and is currently a Top Type university of the Top Global University Project by the Japanese government.",
                "data:image/png;base64,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"
        );
        PU pu4 = new PU(
                "Harvard University",
                "Harvard University is a private Ivy League research university in Cambridge, Massachusetts. Founded in 1636 as Harvard College and named for its first benefactor, the Puritan clergyman John Harvard, it is the oldest institution of higher learning in the United States and is widely considered to be one of the most prestigious universities in the world.",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTEQXJY4guH11f13rcw8r6wH08WFJ4TXrFvX8G7w6QAvU8RAAssFgVPXkpE_bPoO4gDbCw&usqp=CAU"
        );
        PU pu5 = new PU(
                "University of Melbourne",
                "The University of Melbourne is a public research university located in Melbourne, Australia. Founded in 1853, it is Australia's second oldest university and the oldest in Victoria.[9] Its main campus is located in Parkville, an inner suburb north of Melbourne's central business district, with several other campuses located across Victoria.",
                "https://upload.wikimedia.org/wikipedia/en/thumb/e/ed/Logo_of_the_University_of_Melbourne.svg/800px-Logo_of_the_University_of_Melbourne.svg.png"
        );
        PU pu6 = new PU(
                "Stanford University",
                "Stanford University is a private research university located in Stanford, California. It was founded in 1885 by Leland and Jane Stanford in memory of their only child, Leland Stanford Jr., who had died of typhoid fever at age 15.",
                "data:image/png;base64,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"
        );

        PU pu7 = new PU(
                "Massachusetts Institute of Technology",
                "The Massachusetts Institute of Technology (MIT) is a private land-grant research university in Cambridge, Massachusetts. The institute has an urban campus that extends more than a mile alongside the Charles River.",
                "https://upload.wikimedia.org/wikipedia/en/thumb/4/44/MIT_Seal.svg/800px-MIT_Seal.svg.png"
        );

        PU pu8 = new PU(
                "California Institute of Technology",
                "The California Institute of Technology (Caltech) is a private research university in Pasadena, California. The university is known for its strength in science and engineering, and is one of the world's top research universities.",
                "https://upload.wikimedia.org/wikipedia/en/thumb/a/a4/Seal_of_the_California_Institute_of_Technology.svg/1200px-Seal_of_the_California_Institute_of_Technology.svg.png"
        );

        PU pu9 = new PU(
                "Yale University",
                "Yale University is a private Ivy League research university in New Haven, Connecticut. Founded in 1701, Yale is the third-oldest institution of higher education in the United States and one of the nine colonial colleges chartered before the American Revolution",
                "https://acrl.ala.org/residency/wp-content/uploads/2014/12/Yales-logo.jpg"
        );

        PU pu10 = new PU(
                "Princeton University",
                "Yale University is a private Ivy League research university in New Haven, Connecticut. Founded in 1701, Yale is the third-oldest institution of higher education in the United States and one of the nine colonial colleges chartered before the American Revolution",
                "data:image/png;base64,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"
        );

        PU pu11 = new PU(
                "University of Cambridge",
                "The University of Cambridge is a collegiate research university in Cambridge, United Kingdom. Founded in 1209 and granted a royal charter by King Henry III in 1231, Cambridge is the second-oldest university in the English-speaking world and the world's fourth-oldest surviving university.",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRrTzvTzJu_XZKU3p2fK60eTYuoAnjE9RXsRg&usqp=CAU"
        );
        PU pu12 = new PU(
                "ETH Zurich",
                "ETH Zurich is a science, technology, engineering, and mathematics university in the city of Zürich, Switzerland. It was founded in 1855 as the Swiss Federal Polytechnic School, and is considered one of the most prestigious universities in the world in these fields.",
                "https://yt3.googleusercontent.com/ytc/AL5GRJV4jITdFGaL5TpMqszlXNLfPelkEAwe9M0c_FY_JQ=s900-c-k-c0x00ffffff-no-rj"
        );

        PU pu13 = new PU(
                "The University of Hong Kong",
                "The University of Hong Kong (HKU), is Hong Kong’s oldest tertiary institution, with a history stretching back over a hundred years. The University of Hong Kong is ranked 26th amongst the most respected comprehensive research-led universities in the world.",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTDss-lg29Eu-4ztYgiFR7wqDr4vR_5OgjqpE8wKbdgOypHgXa1x7geVU1n_8UAJ8guqX8&usqp=CAU"
        );

        PU pu14 = new PU(
                "Tsinghua University",
                "Tsinghua University is a major research university in Beijing, China, and a member of the elite C9 League of Chinese universities. It was established in 1911 as a preparatory school for students studying abroad, and has since grown to become one of China's most prestigious universities.",
                "https://cumulusassociation.org/wp-content/uploads/2021/10/tsinghua-university-logo-hd-png-download.png"
        );

        PU pu15 = new PU(
                "University of California, Berkeley",
                "The University of California, Berkeley is a public research university located in Berkeley, California. It was founded in 1868 and is the flagship institution of the ten research universities affiliated with the University of California system.",
                "https://logos-world.net/wp-content/uploads/2022/02/UC-Berkeley-Symbol.png"
        );
        PU pu16 = new PU(
                "University of Chicago",
                "The University of Chicago is a private research university located in Chicago, Illinois. It was founded in 1890 as the University of Chicago by John D. Rockefeller, and has since grown to become one of the most prestigious universities in the United States and the world.",
                "https://upload.wikimedia.org/wikipedia/commons/c/cd/University_of_Chicago_Coat_of_arms.png"
        );
        PU pu17 = new PU(
                "University of Cape Town",
                "We are committed to academic freedom, critical scholarship, rational and creative thought, and free enquiry. It is part of our mission to ensure that these ideals live; this necessarily requires a dynamic process of finding the balance between freedom and responsibility, rights and obligations, autonomy and accountability, transparency and efficiency and permanence and transience; and of doing this through consultation and debate.",
                "https://logowik.com/content/uploads/images/university-of-cape-town-uct5693.jpg"
        );
        PU pu18 = new PU(
                "University of Witwatersrand",
                "Wits academics regularly publish world-leading research in the fields of natural science, medical and health science, social science, humanities and engineering. In fact, Wits is home to 381 NRF-rated researchers and 400 award-winning researchers, who regularly contribute new research in their fields and have a direct impact on policy and industry.",
                "https://upload.wikimedia.org/wikipedia/en/c/c7/Logo_for_the_University_of_the_Witwatersrand%2C_Johannesburg_%28new_logo_as_of_2015%29.jpg"
        );
        PU pu19 = new PU(
                "The American University in Cairo",
                "Founded in 1919, The American University in Cairo (AUC) has become an essential contributor to the intellectual, cultural, and social life of Egypt. AUC has nearly 35,000 active alumni. Currently, some 6,500 students are enrolled in 36 undergraduate, 44 master's, and two doctoral programs. More than 16,000 students enroll each year in non-credit courses offered through AUC's School of Continuing Education.",
                "https://yt3.googleusercontent.com/ytc/AL5GRJXrSFplgTd8yQ4U6dyyxyLlHPFNL9MW00-xdg2kvg=s900-c-k-c0x00ffffff-no-rj"
        );
        PU pu20 = new PU(
                "University of Johannesburg",
                "The University of Johannesburg (UJ) is an Afropolitan international university with an identity of inclusion, auniversity that is transforming lives and diversifying professions. A proudly South African university, rooted in the vibrant and multicultural city of Johannesburg, reflecting the city’s energy and embracing its diversity with equal passion.",
                "https://upload.wikimedia.org/wikipedia/en/thumb/a/af/University_of_Johannesburg_Logo.svg/1200px-University_of_Johannesburg_Logo.svg.png"
        );
        PU pu21 = new PU(
                "Stellenbosch University",
                "With roots going back to 1866, Stellenbosch University is one of the oldest universities in South Africa. A century-long tradition of quality teaching and research has ensured the University a place among the finest academic institutions in Africa.",
                "data:image/jpeg;base64,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"
        );
        PU pu22 = new PU(
                "Cairo University",
                "Cairo University has successfully been undertaking its mission of delivering education, research and cultural duties over the years. It is considered as the mother university among other younger universities in Egypt. Cairo University is also offering its education and research facilities to Arab and foreign students and scientists and has become well known world wide.",
                "data:image/jpeg;base64,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3JvYk38etwgxeXGabLQzPpCH5KsPjJljBBu2F+jc3S/MA37JFiHLSYfT4/SQ1krH09Qx5AkjNnscx2oa7q02uL6tPLx6BGzKALk2AFybk26k9SuPA8MipIY6aBuWNgs0cyepc49SSSSe8qQQEREBERAREQEREBERAREQEREBERAREQEREBERBF7RYLDWQmCoaS24c0tOVzXN5PY73rh3qpz7ujM9orsRq6mGMgtieQ0G3LiOB7Xz2B56i60BEEFtdijaCgmnYA0RMtGAAAHGzI2gcrZnN8iht2eHtw/CxNObOe11TO48+03N2vmYB5bqwbVYNHXU0lJKSGyAdoc2lpDmuHzOaNOvJUzEtnMbqIBhs09EKezWvmYHiV8bbWBZ4IJsLgWB7+dw58M2xrYsInxacseXzf8ATRuAAax0oblJbYutd9r9GDXVWTBtsGy4bLXvZlfA2XjRA6tkiBJjvbS+mvylCb1qOOKjocPiFmvqaaJre9jQRr3m+XW3VV3ek12HS1gZfgYlHcADRszHtz/Nma5xv1zeJBbqrbqXiUTIaXN68hklY10mR12sc5rLhpHas3XpmUjsftYyupZJcvCmiztmiJ1ic29ibgaWHMjmCOirG1dN63nwA2tkcyE6fCbCwD+q928LCJqOd2K0LHPErTFWRN1zh4ytla0cyCR5QD1cgr0FDVVGEU+MskkfWU0kkoe4lznxtkIczxgZSbcrZx1Vj2bx1kcLMZxHEi8ysLWQMGSNl3DNHHCLukeHNtm+821Vg3W0L4cLpoZY3McGvLmPBBGeR77OadR4XIrzs3sNh9E90sMIdIXOc1z+2YwSSGRC1mgXtpr3koObA9lzDXur6OUR01QzNNAWntPOrXMGmTnfvGot2uzb2gDkAL6lftEBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQeqSNrrZmg2NxcXsRyI7lyY1hFPVNEdTCyVoNwHC9ja1x3aFEQe+eljkI4kbHFhu0uaHFp+E240PjC6URAREQEREBERAREQEREBERAREQEREH//2Q=="
        );
        PU pu23 = new PU(
                "University of Pretoria",
                "The University of Pretoria (UP) is situated in the heart of South Africa's capital city. It is one of the largest universities in South Africa and enjoys an impressive research and community engagement output. UP is a diverse and dynamic leading research intensive university in South Africa which is responsive to the needs of society by finding real solutions to real challenges.",
                "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAALQAAAC0CAMAAAAKE/YAAAABYlBMVEX///8AWqvEizvaGzPp7PPe4u3w8vf5+vzj5u/m6fGrttPIz+GwQHTFhjoPXKz88vHR1+d8kcCVen7GzeCrgWi4wdrW2+nz9fnlkZWurMDIgTrKeTmffHOptNKjk56uhGavcJe+u8ePdoAlYa7McTjJfDnRYDeFmMRJcbTPZzfTVTagfHBqhbw/bLOVpMnXOTRGYaF5bo/VTTWDcolcZpy3hlbMdDhva5SKdIRbe7djaJq/iUbObDjQYjc0XqZOY6CzhFzWQzXl0b/v49lngrqNjKu7h0+jTnzqqKl2bpKwj3+ZenjUuaPJqY+7nYyOn8ezqazc2dx1eaKloLJwfaqRg5alpbuegoOxqreFgaCWlrKgjpiSeqV6YpW1h6WhdHLWITzBNV7QJ0itTXGkYXWiZXbAtcrKLVO+N2SSSIilVnd/TZKtS3JpVZvQKEfcO0XfX2Puvb702NjfXmPGnnfXtZTh+8d4AAATjElEQVR4nO2dCXfjxpGA0RAOHkuIIAki4jFcA7zEm6IkgqSG1GU5EjQjO7I99kw2x17xZnfjxBv//62qBkgAGq3H721ETJ5qhiLY3Wh8KFRXVTcgShCe5SOXZD6f3DbDzxR1ryCKhT112xw/S4wr8fRUXOxtm+PniPpGdN03L0TX3DbJz5DEqegcfZ4WT+Vtk/wMSRbEXOELQyx8RGNR+twST7/6pia+2zbJh4ty0/1VRQSpfDzWYdZynyYSb0TxTWLbKB8kya9evPj0Xc6BNyeXc16QfAqvONt2onZUs67EkCys2tFpnHWuGMXk28mk0l14wIXKZPI2WTSUbZP9lHzuXt47XWTuOvfp2ONy+WJxrSTeuldX7tuPx3sIbyp3xcS3Vu5Xspz4KFImJZVNG9mkksh/+WU+oSSz+Xw2FW8Tkb7+6hNft0nfY6iffPW1tC2iD5BEofKJv51c++ZPKrU4u7xn6KeS/GPQ+e3wfICoe73HoPWvY+r71PvC45ru3seT+uuFKz8GLbuLr7dD9X9L/kh8HDqRE69iaNcmZP2V4uOahjlB/AKjAdnoqcGhVTmdTSaTUjYtoyWjTdcg6TO2zfhAXmAuOihAZpf+pnIkirVvvwXQo8o3afltpTbABDt201ylW1sUXswL86ujhTdhsbyNo6t5bf6iINYKcctA5MKXixeCUbNynhyenq78bauWEu4XeqG4bcqIZN9mr94J0p4vbwuVSuHt3t6346/woyS8WECTbVNGRE4kToPrSfKkW5jAxKXoVMhpSIVTORm/iYzqioFF0rx4dCSCZ1anhS/w855YieVi5L14ur7+5jscg+9AyYPa5/C5WBPvt4j2uGSPxG6ab0r3fAnhhSQY7g1UdcWr9HbpHhMgPb1PJxLpvYq/UtPdu5taxv2pGFNFg69Gm1jUThehBabT2sKzlHiKcn8kvleu7mPLDJJ+cfoQ+fRdTO15LfLeu0LAPhaFd3vxc8/vESn/zRtaOr16883HdDNRTeN9xHQ8p1gbyd6/e/frf0T59TuUf/rss8/87W+p/DdxSz0gywNz+O3vdkB+T2Ox+8+4/QcsFmu/36GKuBn3J+KVeLr4F4L7V9wW/0jbf6Ttf6PtnX/fNmVE8gt34Sy6f+LqrSxyR4U/4PafuoWjSu3oO9z+Xdymturb6XQ+d/6Dq3c8v17l/pO2/8sZTZbufyP/b2IzLnd/4cl3/xCQCpffBsu+81tuGxmgd94nXmjJvLdyd9vMG+hhc/hy+HFBZ/brB5nO/n5mGIUedppnHm2neRAraFEsN8vN44x4FoFu4nsZyw4ysFUaxgd6KIqNkpgpi1HojljqHNSR+kzMNA9OxFKMoEvlA4DslEsHYegG2UsdzqVO57MvdmIDvdOp75/UT/brzchA5JbREZs7jRJunYknsYEeZo6PAbJxEh2IGUJtgn4b5EoOxJexgT4o7ZczmUypWeqEoU9Ax2A8NCJPYKsBRhIX6J3j8n65XN5vlCPmMSyJpQYYO5Q1YCsj7sdnIO6UGi9PTk5eNhrR4DI8yYhlPjr3S2KjEyc/3Sk3UJPH0YEY44jY2WmeNYcHEMebO52PAvq4s39wfLDfOd6pd+pnx51m53gYd+ghWMXL8ksIiEMx02iWX5ZL4kEU+qB+vD+MFbRYPsb/4hk46jr+fwBdpw8H8YE+y5QxKWqWMwebrTB0UywPdzqZTHyghyflJkTw5vHJcL/8cp+2zsLQPBg2xU5soHfqaBricbm+U2+cYB56HB2IJfLeB2IzNtCQ+5eQr5PZF5sNRO1kmnHX9PC4/LJer78sHw/rDdo6Lp/F3abh4u+XSqV9SD1L5RPYOik1on467D22jQyys18+rjeOIWPaaZbL9Ua5Xj55EBFDfnrbxCDfDzsQDw+OO2cwhz2B6Fg+6DQ8aLGxJt3I99smBvnzA6pORlxLpvOg+s/bJgb5RRSqLoakHq2PwQqT8Msw0llJjEjpLNzil9smBvlLiKgZRUZphpr8ZdvEID8EeIbl9zGL6KY38sO2iYXQCuRBYASKi+A90LWP3olDbAH5H59mP6jc2moVuqW4HyfnsR6Jw0aQMacxZrvBEt9lx2Ec+kYddM7i1ZSRTEMm0omNSXtGfRJUamHJPBnVguXosr+PhUljeAk75wkLiBWsAZcdh9CC8kPIOR8dspDMQ7+s04yHdQiCGrTcCjcNbTnyZLnsBqoX24Zdy+2Gaezp1+3mCly6h2yyOavbbbOuZddHqq18oyh0XQ+6UGFs47JjMgxRfuRE6JwfQhcCLjs+ivZUzZ3zhIs1XkN36SyuFzFTNFm155wnXWsyGU8mc8uHtjYuO06KBlVfec5Zm1y53UpQTk+9UKNZi1gpWhDyni1Pj8Ae4F+hBu/dXMV1XWdt6LF7/krnNuC4NTdndbk4OXeS6/pekOnbZnwgks3scdc9HFljsGgLZVKZTK/H7sQzj37cnvkGSbP52AbnBqPQGSPnyKo4oG3nOq7GgTLwh6LlTkZkK5WpYznWiJdXt833XlF1byyCfXhbldX1xPN4emweqwmLOfM88jqYr3I5bzpwGctHvlGkNntE2jF+QDb5CHU7Po7jnKLyxV8Dce791LMA8+5fL/Dt9uJpWT25eD2fwJHPneWrQKk0e8h8GbSNVyPnHHaeXL96+pi+ezuBjPPm4tUchlswDzL1KLMeHIO3MBmbv9q9AYduPXX6dF4h7zvhqd08eHh1EGauBn3d7RyLlgXyiaubp7QR0i+mbZgLafMItZC3N8h2KA4i8wrOU3Msmi88oY3cOnTEURfRD120k1Xo6Mne2jSCbmP3FTjwsUNT33mXLtXT2cgr0uQITWNpjS27Ahd7GbrUqkFNbCNoGhc3sMPk2nUO+e5Erb2K9v43kt0cadEFvTlOZeSO6PPkNqhs6Y6xQSii3KIdH16Pna5roZVUqBPrycz6nEza7oLmxlN3NJ6yOaht9OY82EgO/YI7H7nTcY6Nr3Ng1ssuDeHDc+HJ5NWSG7XNlrnDyZgtXZcQ3py/d2Dtnt+QSVhgTQ7LaSM4YW7ST2UcRHFD13ZVQWpIiVz4idYCOfSrKPfu+a1FGZTrznO2e23B+WkVvnZWedIAc8ETzsMKDbgxTlDYmGfOh87N7fnFxS7IxcX57Q0fd0vr0NLc6fhwYsMUp8uZnSeO5ed85rdC01x255bjLl3t2p1zTzeaX+Pix3juTQDAiHLMGa0cx0FP4zFfP6FBe9Q8UR514dIfjlfgul3wuitnbAdCCwHPLXc8Z2N37h7WliM8UX4q8y2sgtxyaruCc0Ccq0xWcPGn7vXIGjnT1dJeshHY+LhyaI0s1xm5MCWwQMVjb111G8xr6uURUoymUxiNLDeZOMsCuonpHP33cpKztGl3ypaVawfbLytHnHm6pdUmz66nfBVvPgIroRUxC6An41XhEEZr1712R10HrJqhvV9fidzsx1tbITvnaY8rigUcWiPbWU6cqWtp1mo8WeVw0KGymTWfXGODw4Io8mjqPPkY3AilE2Agoihyj7BcWVOnsHQOp9Z8iqkUKLsyWfHoh3cEjnCYLp80J30gu5Skzvk9rWue/GnT1XTkutNDd7pic4jYNF6v6be4yDjmW5i0hOU8B7rL0d2Ui9s3U9/fgfPQ1kvs9tSFOSEuUIPi7dwWTcOX3VfXzD7y1p4vbl9b09HmhgDTRlPrNZ/F3oriqc2mW1czl4ub+XyzyL8Lsfv16xsXpPL6NUT0dYUoHh7exEDNnpzf1H786VY/FmKEjHL+Af7gIl7Iz/Isz/Isz/Isz/Isf/+iyqYgyAq8JEFIwkuRva/pg0JTBsE2Mn39nSBlZdpFhVYggQamX4KCXWEj6gt/qvCRH06SZVpt99piz1Ar8TdBzWZVr5Ru1yQlwTteWJJMFlSWFwTWVoVWShAMTRBSNnTPikIWpyAqtGGsj18XZmvMwF2SgoE1eSGNUxTeFjcZ/za/3kAQ8hocqcrw7A1oKDN+4BTj94/wBl4KdwRJUim8KTONzRRe2scvftPptnqaRb/UZAMNP3xoGQrztgrQEm+TUHRdEO5aQqqvErQp6bpkQn/8CgC0KbV6Er+dZfSprSDM6CwMNlPX0DMhYUNhO2WmmAI70p0ZRbKrkioMZqYyG1B3ij5bQ9+xwaPQs7bqQ6t9QxjAUdfQoFpQ/p2uqhL/KAitFilhDY3VXp8yS2AP0LCFRcbMzgeg6UK0U0ICdpJ8JdpwZFUz+AljdwbzoVW71Y/YxwY6b+d9aCTu5xF6NpuZ0CafneloHv2qEoGezS4fQAOxjKdrzIqgTcHQq7M1dFtOI1t7UBxoqOn2bCZ70HQGadK/ke31fOgiS7DiY9DZ6kz3odN2EY+aZSnDQHtosxbqXEr1Z2oY2jCMB9BwzgZe3Z6ex54NXbEHG5vWBrDZbrNZFnccGPS16xHoPr87RtDVdrZfjUKDOXJoxdZ8aFMjm1qbRxbtUMgnBDyXnzQPIWv34DAK0zSthdBCVVubh0ob7aqOz4GEzEPYmIfRx88cug29tMPQCkupWdwVXlXmQws6PQ+TZbJpkj0YdgIKW0IeFBGClrBBFNrUsADbGraJ0IodsGmCTkk2XaK8aao+9GCmKG0aiNm8JnvQMnRVZJGvF6oy/vALQCv2GtogDHJ5JlH2eqpQ1Gw8lxA0yENoQQfXQ59RlQANR4lCw4Fk7vKKPjS4PO3S5DvpYIcEbUBXNKzDBkIRQ0hgazAvhW6omfRF7mYCBPaBFxUoPLhQnST5DUIl3vXD00qisSYUcGjYIlDB69QEvkBM3gx7LhYpuECR6fNQr1J8Hht5lmf5W4gqobvEsaCA1ZuKX4SfqFyVJBoGquSPBqlIo8akkYi1Xg/K+ja+WuTjzqsUeHOJ1yuSl6JgrX8AqlJ4x4LktVC9naVIFN9ERKFnK0KV+zEFEz3IPzGdyfJEjt7pmaS0xmz0UuQbU57PAhdu2r5fwoew7vBAGiVvXPDpXx3370F/1LLP00IZu8CUKG1vKmwk8kKPxFKPQ0PdBhoTvTT4aAwwpOIsK8ro+YXZQOV5EEIrictBglpJedt/ZKmnmzIdVatuNJ1nCb5/7y6ZolPpD7imEwMbPZ5iV1XDq0hWNWUNbbDLx6HbtrmBFjDR0wmWXzMM6VUsaFe5ZfAoJOikBrVt9PwEgY5FkUYLxIQ8hBfqEJI8TtP3FYjpO2bgJmR0Bq9ALB9a11nETQegU+1UABoSvbZBsLrO4bPF2R0/cXpuJgQNgWttCJRrpfoIPWu1fEPfaLolpxjafH+m6+kNNO2hD0jT8mCjaZMVowExAG3k+4MNdFaTqessq6ZS9N5nnKDIs/IwtGm3/C4D0Hq1uoH2xkTPZm1uur1Uqvhe6D5rb2w6zeRW5KlxzPJ8aLWtbaBNplNutTEPg/pQswrnDUMLs1SwS2GA+XDYPHioBpvWe2ToD8xDJaOEH9UeaYBD44NzWviJVUgDYdClCRqu/AbaS/SCNj1o47gBs8P88XFoYdZSKYd7YNMcesAro9AStE5TOtdPJbS1plU7ZUpa5KsNB8zWLlUOrbYD0FyxPNPj0Gab23SbT/0ehZbttk3KfASaEry1y/OhqeOqdzYpW/Kgi2ik+l0YWijmad4uFKFZAjtT+Of1G4j3MZk1aQdKCyWevxe98VcMfPmq4nWZDYx6yZ9SF2EQUbaIHfNJhleV4B0L2SS0SHBL5PslYvclo8/yLP+vopg8t1PRY1COp2COh+OI//ByNxU2KLcwkzTGcD6LZYrp77veZV3rb/mH2nSl+gV0MMWrUZNmpKEQ6cKTu0tMgQRKjmjGmkS3g+GBFhYEmzFaTcHFO4xLkIr1MVBi6CqiN6zytETa7CJgoFBgbo1o7XWgxJyvjV7AX/vwFvLwEAwdv9zmWZ4Kn9sy9UL7tWZRaMjmyBMGoCFFDUBX5R7ldCyRQP/UHki9Ow/aLN710dM9hIbpM899ZKat9aSlIJ/DrHeWoEUciGs4bRaKBjOKmHPdSQaiqMyQKRpzaFNjD/46U29AuVcAul8NQhtCtU3QvDl8pr+vQEmCkPIifRRaqM54pmr07LWLhbBGC1wpvLjVwEpNguJgEY8OKSRAp3mA49DZ/uWDP62S79vZMLRhKwHoyxalxga7u0POKmtRZhaG1u/0MLSk8aWsXqq1XvLUDDNFudRMKmq0UqPf3SXW0HzJsUWaTg5wzZFDD+6qvSi02ieTCUBLs1QAWq+2cS3HYNUqBeo8z0nC0IPqIAwt3FEOrLB81fanARpfk4YQrdHERmKtalXaQCNii6Bh3KAiCVrtp/LRfNqP6wFoJd8PmQdOYnzzwFX56jqHfNw8aGnY+w1Lf8lTq/I7Bal+in7PKGIelJldDkjTPJci6CJfvn8vdJamV7T6rKizEHQaB4IHjYXGh0O3dElq+zMaP1VL9VWaTUSgFdJPlmyaL+URdLUtSfqD38Lk0OqlPdOwDqBBQRto26Z81WD9PhIO7B6lZh8EbWLTqr/kuYEG9ZH3sPtkBhwaDtGjmQQORFrKI2gcsnkt6qqL/PKZeYNSMzOtCir+7Us1jZaUTqdpfS0BGzQCswa1L/IFQD5dTpuw23oXrj1srGCh5H+VfTq5qcLEUcUuab6R5kwyJ8BeFOxJSntdmh/L3+L8+5H/BRwqUo76F29uAAAAAElFTkSuQmCC"
        );
        PU pu24 = new PU(
                "Ain Shams University in Cairo (ASU, Cairo)",
                "Ain Shams University was founded in 1950 and is located in Egypt. The university offers both undergraduate and postgraduate programs and has seven campus locations. Four are located in Alabassya, two in Heliopolis and one in Shubra Elkheima.",
                "https://i1.rgstatic.net/ii/institution.image/AS%3A267464833732608%401440779993119_l"
        );
        PU pu25 = new PU(
                "Alexandria University",
                "In 1938, the nucleus of the Alexandria University had its beginning in the form of two satellite faculties of Fouad the First University. These were the faculties of Arts and Law. The faculty of Engineering was then established in 1941. In the light of the need for developing more disciplines for higher learning and with a view towards meeting the need of the people of Alexandria, Alexandria University, formerly known as Farouk University, became a separate entity in August 1942 with four additional faculties: Science, Commerce, Medicine and Agriculture.",
                "https://www.logolynx.com/images/logolynx/40/409af145ae9b1520a8dc8c46c2c4a4f6.jpeg"
        );
        PU pu26 = new PU(
                "Rhodes University",
                "Located in Grahamstown in the Eastern Cape province of South Africa, Rhodes is a small university which enjoys the distinction of having among the best undergraduate pass and graduation rates in South Africa, outstanding postgraduate success rates, and the best research output per academic staff member. This is testimony to the quality of students that Rhodes attracts and of academic provision, and to the commitment of Rhodes staff to student development and success.",
                "https://media.licdn.com/dms/image/C560BAQGvEQhg8sYz5A/company-logo_200_200/0/1519856042104?e=2147483647&v=beta&t=SW9VHrbny7lfQt_fTbyLPYRIWw6raXTNcWNF5dTbAcs"
        );
        PU pu27 = new PU(
                "Fudan University",
                "Ask any would-be Chinese undergraduate where they most would like to study, and chances are Fudan will be mentioned. Based in downtown Shanghai amid the bustle and beauty of the urban sub-center of Wujiaochang , Fudan is a comprehensive research-oriented university renowned both at home and abroad for its highly-rated arts, sciences and medicine disciplines.",
                "https://res.cloudinary.com/crunchbase-production/image/upload/c_lpad,h_256,w_256,f_auto,q_auto:eco,dpr_1/tgbuhwwtbm7pr5burihb"
        );
        PU pu28 = new PU(
                "Zhejiang University",
                "Zhejiang University (ZJU) is one of China’s top higher education institutions, as well as one of its oldest; its roots can be traced back to 1897 and the founding of the Qiushi Academy.\n"
                + "Located in Hangzhou – one of China’s most picturesque cities – the University is organized across seven faculties and 36 schools.",
                "https://www.fdtgroup.org/wp-content/uploads/2016/11/zhejianguniversity-memberlogo-300x300-edited-295x225.png"
        );
        PU pu29 = new PU(
                "KAIST - Korea Advanced Institute of Science & Technology",
                "Formerly known as the Korea Advanced Institute of Science and Technology, KAIST was South Korea’s first research-oriented science and engineering institution when it was founded in 1971. \n"
                + "For such as young university, KAIST’s standing is growing rapidly: it is widely acknowledged as the best university in Korea and has been named the most innovative university in the Asia-Pacific region. ",
                "data:image/png;base64,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"
        );
        PU pu30 = new PU(
                "Universiti Malaya (UM)",
                "University of Malaya, the first University of the country, is situated on a 750-acre (309-hectare) campus in the southwest of Kuala Lumpur, the capital city of Malaysia.The University of Malaya grew out of a tradition of service to the society. Its predecessors, the King Edward VII College of Medicine established in 1905 and Raffles College in 1929, has been established to meet urgent demands, one in medicine and the other in education.",
                "data:image/png;base64,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"
        );
        PU pu31 = new PU(
                "Shanghai Jiao Tong University",
                "Founded in 1896, the Shanghai Jiao Tong University is one of the most prestigious universities in China. The main language of instruction is Mandarin, while more and more undergraduate and postgraduate courses are being taught in English or jointly taught with international institutions.",
                "https://universitas21.com/sites/default/files/styles/scale_640_w/public/2018-04/SJTU.jpg?itok=Zuyb0Ov4"
        );
        PU pu32 = new PU(
                "The Chinese University of Hong Kong (CUHK)",
                "The Chinese University of Hong Kong (CUHK) is a research-oriented comprehensive university whose scholarly output and contributions to the community achieve the highest standards of excellence. Founded in 1963, CUHK has been guided by its mission to assist in the preservation, creation, application and dissemination of knowledge and a global vision to combine tradition with modernity and to bring together China and the West. ",
                "https://res.cloudinary.com/crunchbase-production/image/upload/c_lpad,f_auto,q_auto:eco,dpr_1/a0j1tuywmodm654umbqx"
        );
        PU pu33 = new PU(
                "Yonsei University",
                "Yonsei University, established in 1885 as the first Western institution of learning in Korea, is the leading university located in the heart of Seoul. The University’s mission is to educate leaders who will contribute to humanity in the spirit of “truth and freedom” and to foster education, research, medical care, and social contribution in the global community. ",
                "data:image/png;base64,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"
        );
        PU pu34 = new PU(
                "Korea University",
                "Korea University was the nation's first university funded and administered solely by Koreans.  The university was founded in 1905 by Lee Young-Ik, Treasurer of the Royal Household, whose idea and vision was that only by educating the younger generations could Korea secure its independence and grow and prosper as a nation.",
                "data:image/png;base64,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"
        );
        PU pu35 = new PU(
                "Australian National University (ANU)",
                "Renowned for its exceptional teaching, research and small classes, Australian National University (ANU) is ranked by QS as the #1 university in Australia.* It has the highest number of Nobel laureates among its staff and alumni than any other university in the country.\n"
                + "\n"
                + "ANU has led the development of Australia’s understanding of itself and the world since 1946. The university offers undergraduate and postgraduate students from across Australia and around the world a comprehensive portfolio of degrees – including flexible double degrees – and the support of more than 3,000 academic and professional staff.",
                "https://www.logolynx.com/images/logolynx/e9/e9f50dfc47f815c697de151636403c3c.jpeg"
        );
        PU pu36 = new PU(
                "The University of Sydney",
                "The University of Sydney was founded on the principle of giving everyone the opportunity to realise their potential through education and still holds that belief just as strongly today.\n"
                + "\n"
                + "Currently ranked 4th in the world, and 1st in Australia, for graduate employability*, the University of Sydney is also consistently placed among the top 50 universities in the world**.",
                "data:image/png;base64,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"
        );
        PU pu37 = new PU(
                "The University of New South Wales (UNSW Sydney)",
                "UNSW Sydney is a world-leading teaching and research powerhouse recognised by employers and organisations around the globe. Ranked in the top 50 universities worldwide in the QS World University Rankings, UNSW is dedicated to shaping forward-thinking graduates who make a positive impact in the world.",
                "data:image/png;base64,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"
        );
        PU pu38 = new PU(
                "The University of Queensland",
                "The University of Queensland (UQ) is one of Australia’s leading teaching and research universities. For more than a century, UQ has educated and worked with outstanding people to deliver knowledge leadership for a better world.",
                "https://probonoaustralia.com.au/wp-content/uploads/2018/10/UQ_Lockup-Stacked_Purple_RGB.jpg"
        );
        PU pu39 = new PU(
                "Monash University",
                "Monash University Is the largest university in Australia, ranked in the world\\'s top 100 and a member of the prestigious Group of Eight.  We\\'re named after Sir John Monash, and fuelled by his desire for our students to leave here with a greater sense of purpose, and the skills and confidence to create positive change. ",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQhH4dXlGwLudcqFbSoHK4WPaMg8DPohnRoPQ&usqp=CAU"
        );
        PU pu40 = new PU(
                "The University of Western Australia",
                "The University of Western Australia (UWA) is a world top 100 university, ranking 85th in the world in the highly respected ShanghaiRanking’s Academic Ranking of World Universities 2020 and 92nd in the world in the QS World University Rankings 2020.",
                "data:image/jpeg;base64,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"
        );
        PU pu41 = new PU(
                "The University of Adelaide",
                "Since its establishment in 1874 the University of Adelaide has been amongst Australia's leading universities. Studying at the University of Adelaide means being part of a rich tradition of excellence in education and research, with world-class academic staff and a vibrant student life.",
                "https://www.adelaide.edu.au/brand/sites/default/files/media/editor-images/2021-07/uoa-logo-col-vert.gif"
        );
        PU pu42 = new PU(
                "University of Technology Sydney",
                "When you study at UTS, you join a community of inspired students, staff and industry partners who are shaping the future. Located in Sydney’s technology precinct, our campus is minutes away from the central business district and some of Australia’s most innovative companies and startups.",
                "data:image/png;base64,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"
        );
        PU pu43 = new PU(
                "University of Wollongong",
                "The University of Wollongong (UOW) is a leading global university, powered by its people, partnerships and communities. Ranked among the best 20 modern universities in the world*, UOW is located on the Australian east coast, an hour and a half south of Sydney.",
                "data:image/png;base64,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"
        );
        PU pu44 = new PU(
                "Imperial College London",
                "Ranked 6th in the world in the QS World University Rankings® 2023, Imperial College London is a one-of-a-kind institution in the UK, focusing solely on science, engineering, medicine and business. Imperial offers an education that is research-led, exposing you to real world challenges with no easy answers, teaching that opens everything up to question and opportunities to work across multi-cultural, multi-national teams.",
                "https://pxl-imperialacuk.terminalfour.net/fit-in/448x293/prod01/channel_2/media/migration/staff/White-on-navy--tojpeg_1495792347019_x4.jpg"
        );
        PU pu45 = new PU(
                "UCL",
                "UCL is a diverse community with the freedom to challenge, to question, and to think differently. Our community pursues academic excellence, breaks boundaries and makes a positive impact on real world problems.",
                "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAOEAAADhCAMAAAAJbSJIAAAAe1BMVEX///8AAAAUFBSDg4OsrKzs7OxgYGDj4+MmJiYQEBDV1dVMTEzExMSoqKjOzs4EBAQ1NTW2trb29vY9PT1oaGigoKAZGRnh4eGYmJjb29tSUlLz8/MeHh58fHyQkJDMzMxsbGwwMDBFRUW+vr6KiopaWlpJSUl0dHQ4ODhS4ZYJAAAFYElEQVR4nO2a7XqqOhBGQUGQAhYUBBEV0er9X+Gx3YrADBUOgTZ93vXTEJJlSDL5UNS/jvLTFRgcGMoPDOUHhvIDQ/mBofzAUH5gKD8wlB8Yyg8M5QeG8gND+YGh/MBQfmAoPzCUHxjKDwzlB4byA0P5gaH8wFB+YCg/MJQfGMoPDOUHhvIDQ/mBofzAUH5gKD8wlB8Yyg8M5QeG8gND+flRw9D8ZDJsIT9luN2ku0DzPnGiXTrLBivpRwyz1MktX3nir21t997w9ILQqTDO0JyTn948u4a3rT4x39efcJZ8kdfIShSKb3krtu6HvVvFOvU0XOQr8ttMr1fIMGuGpMrxG1egqRk+efROYh2YHFPynNbTUGXK+T+GFmeYGk1697+FNrx4w43hkM9UkOHS/t7vk6D2YuGGi5XFlCLG8EzewuGeq91RtOHq6zOqjyNCDC+NHbCKPq2ULtjQ3P97yVS8YdqqBb8Ud+UoQLDh8v4/58IN2wveBtVd6UMVbLh6VC8UbDhbtxe8TY6XwQyd+0v06pzY2/DqdhG8cR3MMHGnm5mjGzOhhhOto6CSDGMYXs+Xk6e72uGwycpjdl/DFUn9wtcNQ2/on55ww3BzOTlWUZyeR9NDEQz3NWQ7YZzvZtfseHBcTtJ/RFbCDE36IflnQYYXxsDQigAtTG1mqrTmgg3VNLEjI45cw8mVQNPtKLGKaamnIdOEcSX4fXfIA3kaijbM3FR1NDXKw2OiftwWGEZapPUzTBnB6kimboNqun3JHkniDBf2RY0cdafN3xN1GcxUayPIMCZpOl2+eOWsq9LbBzCcXMUabkhmP2CqUnTFWvuKM9zuBzKkQ9i6GjH9499w5Lv19bc4w/lAbTixSFLEVSW8Bf3JPiW/i23DILgZqqZ+G9xmalxMFr0Mz2RZ72dsXQ6JnTJbi+IM32I7iK3AjR3ND7y1HSTPv7qPYUTmupivS/YcP4cxjMibkqMIQ9oNuXGmGWGGB2aPKH+MCD0Mw5ykcDtqYxjGFsF9bCf0MLzuScpR7cIAu4kMPQw3dL7vdlLx6w1X9OvvUkEJDA90adSlgjB8yfCGlz9vyPTDvz/SNBy7NfDrDTc08Kand9/x6w3f6YzPLi0a+fWGTNTWEHk38HsN3Xtw5pEUnx6jf3KM2HBuHMMz2S17bWjfd1vp6km50BJunJQ4YK4rjGNIg8u64YTUI78/wUyILleGaXxeyqArq3EMj+Rgxa8ZmqQe3v1YIKSDqT6jRTy2jXWtNl2OY7h8uQSi29rOI4kONYpLJ/2wlPNaThjH0KTDRW3Ip3cQigdm9PoM3U7cxpXkzXM3bhzDOd11TyrN8EbSS+dzdIWo6LU9tfdcqQxIunZ+rL6poaN2oG0UTDdxFK/UEz/oV2w9k3c0s6JH5esIZ/olG0HWZGg5PLXrFd0MmTWQkhe7jQfmhNd7Zt5y14R8Oy12gTTu9M3eNhk2Uem+XQ0z2ki3v9lbXbPsPLUZ/aT8GZ7YCumxHZx2Jydec9dQksf/196QvfvXeq3GH1MniZ4k7DWZdTlumTfdw/BvNCQVw8lYhrMXF9Lq7Cq5z90y3/CLQ/axDJnw8juMauYJHYtf8DxRGM1w06kR60vAZcfbJqX5cjTDDiVxMxaN3b+jfK9uPEM1eF3GndykuZndjEbscl1HNAzbdkWb3YhZtb7Y5r2ISwczVM1240XecCf9g4neOILqCXF7w4w1nHRgHrb5UHNzwedebFsppuq8nK2D4YYruHVuAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAOvIfyYmfTIJ7fxAAAAAASUVORK5CYII="
        );
        PU pu46 = new PU(
                "The University of Manchester",
                "The University of Manchester offers over 1,000 degree programmes across the humanities, business, science, and engineering subject areas. These programmes include foundation courses, bachelor's degrees, master's degrees, PhDs and MBAs.",
                "data:image/png;base64,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"
        );
        PU pu47 = new PU(
                "King's College London",
                "King's College London is one of the top 25 universities in the world (2016/17 QS World University Rankings) and among the oldest in England. King's has more than 27,600 students (of whom nearly 10,500 are graduate students) from some 150 countries worldwide, and some 6,800 staff.",
                "https://upload.wikimedia.org/wikipedia/commons/1/14/King%27s_College_London_logo.svg"
        );
        PU pu48 = new PU(
                "The London School of Economics and Political Science (LSE)",
                "The London School of Economics and Political Science (LSE) offers you the opportunity to study the social sciences in an institution with a worldwide academic reputation, while enjoying the cultural, social and recreational facilities of one of the world’s greatest capital cities.",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/5/51/LSE_Logo.svg/1200px-LSE_Logo.svg.png"
        );
        PU pu49 = new PU(
                "Technical University of Munich",
                "Consistently featured as one of the highest-ranked universities in Germany in the QS World University Rankings®, Technical University of Munich (Technische Universität München), also known as TUM, was founded in 1868 and is a member of the TU9, an association of nine of Germany’s most prestigious technical universities.",
                "https://softwarecampus.de/en/wp-content/uploads/sites/2/2019/05/TUM_logo_1000x1000.png"
        );
        PU pu50 = new PU(
                "University of Amsterdam",
                "Ranked in the top 20 universities in Europe and the top 60 worldwide, the University of Amsterdam (UvA) stands for boundless curiosity – learning, research and innovation for a better world. Our 41,000 students from over 100 countries study a vast range of subjects from media, psychology and business to artificial intelligence.",
                "https://i.pinimg.com/originals/d0/9e/63/d09e63f51fad7b267d1fe564dc4dec77.gif"
        );

        Long pId1 = pUSessionBean.createNewPu(pu1, cId1);
        Long pId2 = pUSessionBean.createNewPu(pu2, cId2);
        Long pId3 = pUSessionBean.createNewPu(pu3, cId3);
        Long pId4 = pUSessionBean.createNewPu(pu4, cId4);
        Long pId5 = pUSessionBean.createNewPu(pu5, cId5);
        Long pId6 = pUSessionBean.createNewPu(pu6, cId4);
        Long pId7 = pUSessionBean.createNewPu(pu7, cId4);
        Long pId8 = pUSessionBean.createNewPu(pu8, cId4);
        Long pId9 = pUSessionBean.createNewPu(pu9, cId4);
        Long pId10 = pUSessionBean.createNewPu(pu10, cId4);
        Long pId11 = pUSessionBean.createNewPu(pu11, cId2);
        Long pId12 = pUSessionBean.createNewPu(pu12, cId6);
        Long pId13 = pUSessionBean.createNewPu(pu13, cId7);
        Long pId14 = pUSessionBean.createNewPu(pu14, cId8);
        Long pId15 = pUSessionBean.createNewPu(pu15, cId4);
        Long pId16 = pUSessionBean.createNewPu(pu16, cId4);
        Long pId17 = pUSessionBean.createNewPu(pu17, cId9);
        Long pId18 = pUSessionBean.createNewPu(pu18, cId9);
        Long pId19 = pUSessionBean.createNewPu(pu19, cId10);
        Long pId20 = pUSessionBean.createNewPu(pu20, cId9);
        Long pId21 = pUSessionBean.createNewPu(pu21, cId9);
        Long pId22 = pUSessionBean.createNewPu(pu22, cId10);
        Long pId23 = pUSessionBean.createNewPu(pu23, cId10);
        Long pId24 = pUSessionBean.createNewPu(pu24, cId10);
        Long pId25 = pUSessionBean.createNewPu(pu25, cId10);
        Long pId26 = pUSessionBean.createNewPu(pu26, cId11);
        Long pId27 = pUSessionBean.createNewPu(pu27, cId8);
        Long pId28 = pUSessionBean.createNewPu(pu28, cId8);
        Long pId29 = pUSessionBean.createNewPu(pu29, cId12);
        Long pId30 = pUSessionBean.createNewPu(pu30, cId13);
        Long pId31 = pUSessionBean.createNewPu(pu31, cId8);
        Long pId32 = pUSessionBean.createNewPu(pu32, cId7);
        Long pId33 = pUSessionBean.createNewPu(pu33, cId12);
        Long pId34 = pUSessionBean.createNewPu(pu34, cId12);
        Long pId35 = pUSessionBean.createNewPu(pu35, cId5);
        Long pId36 = pUSessionBean.createNewPu(pu36, cId5);
        Long pId37 = pUSessionBean.createNewPu(pu37, cId5);
        Long pId38 = pUSessionBean.createNewPu(pu38, cId5);
        Long pId39 = pUSessionBean.createNewPu(pu39, cId5);
        Long pId40 = pUSessionBean.createNewPu(pu40, cId5);
        Long pId41 = pUSessionBean.createNewPu(pu41, cId5);
        Long pId42 = pUSessionBean.createNewPu(pu42, cId5);
        Long pId43 = pUSessionBean.createNewPu(pu43, cId5);
        Long pId44 = pUSessionBean.createNewPu(pu44, cId2);
        Long pId45 = pUSessionBean.createNewPu(pu45, cId2);
        Long pId46 = pUSessionBean.createNewPu(pu46, cId2);
        Long pId47 = pUSessionBean.createNewPu(pu47, cId2);
        Long pId48 = pUSessionBean.createNewPu(pu48, cId5);
        Long pId49 = pUSessionBean.createNewPu(pu49, cId14);
        Long pId50 = pUSessionBean.createNewPu(pu50, cId15);

        PUReview review1 = new PUReview(
                4l,
                "I had a great experience at this university. The professors were knowledgeable and supportive, and the campus was beautiful. However, the administrative processes could be more streamlined."
        );

        PUReview review2 = new PUReview(
                3l,
                "Overall, I had a positive experience at this university. The classes were interesting and the campus was nice, but the facilities could use some updating."
        );

        PUReview review3 = new PUReview(
                2l,
                "I was disappointed with my experience at this university. The professors seemed disinterested and the campus was outdated. However, the location was convenient."
        );

        PUReview review4 = new PUReview(
                5l,
                "This university provided me with an incredible education and a wealth of opportunities. The faculty and staff were dedicated and supportive, and the campus was modern and well-equipped."
        );

        PUReview review5 = new PUReview(
                1l,
                "I would not recommend this university. The professors were unresponsive and the facilities were run-down. Save your money and go elsewhere."
        );

        PUReview review6 = new PUReview(
                4l,
                "I loved attending this university. The classes were challenging but rewarding, and the campus was vibrant and diverse."
        );

        PUReview review7 = new PUReview(
                2l,
                "I was disappointed with my experience at this university. The facilities were outdated and the campus lacked a sense of community. However, the professors were knowledgeable."
        );

        PUReview review8 = new PUReview(
                5l,
                "This university exceeded my expectations in every way. The professors were inspiring and the facilities were state-of-the-art. I am so grateful for my time here."
        );

        PUReview review9 = new PUReview(
                3l,
                "My experience at this university was mixed. Some of the professors were great, but others were unengaged. The facilities were adequate but could use some updating."
        );

        PUReview review10 = new PUReview(
                4l,
                "This university provided me with an exceptional education and countless opportunities to get involved on campus. I would highly recommend it."
        );

        PUReview review11 = new PUReview(
                4l,
                "I had a terrible experience at this university. The professors were uninterested and the facilities were run-down. I regret my decision to attend."
        );

        PUReview review12 = new PUReview(
                4l,
                "Overall, my experience at this university was positive. The classes were challenging and the campus was nice, but some of the administrative processes were confusing."
        );

        PUReview review13 = new PUReview(
                5l,
                "I had a fantastic experience at this university. The professors were knowledgeable and engaging, and the campus was well-maintained. I am proud to be an alumnus."
        );

        PUReview review14 = new PUReview(
                2l,
                "I was not impressed with this university. The professors seemed uninterested and the facilities were outdated. I would recommend looking elsewhere."
        );

        PUReview review15 = new PUReview(
                4l,
                "This university provided me with a great education and plenty of opportunities to get involved on campus. The faculty and staff were supportive and encouraging."
        );

        PUReview review16 = new PUReview(
                3l,
                "My experience at this university was mixed. Some of the professors were excellent, but others were unimpressive. The campus was nice but could use some updating."
        );

        PUReview review17 = new PUReview(
                5l,
                "I absolutely loved attending this university. The professors were passionate and engaging, and the campus was full of opportunities to learn and grow."
        );

        PUReview review18 = new PUReview(
                2l,
                "I would not recommend this university. The professors seemed uninterested and the facilities were outdated. Save your money and go elsewhere."
        );

        PUReview review19 = new PUReview(
                4l,
                "I had a great experience studying at this university. The professors were knowledgeable and helpful, and the campus was beautiful."
        );

        PUReview review20 = new PUReview(
                4l,
                "The workload at this university was extremely heavy, but I learned a lot and felt well-prepared for my career."
        );

        PUReview review21 = new PUReview(
                4l,
                "I loved the sense of community at this university. There were always events and activities to participate in, and I made some great friends."
        );

        PUReview review22 = new PUReview(
                4l,
                "The dorms at this university were not great. They were small and poorly maintained, and the food in the dining hall was not very good."
        );

        PUReview review23 = new PUReview(
                5l,
                "I had a fantastic time studying abroad through this university's program. It was a life-changing experience."
        );

        PUReview review24 = new PUReview(
                5l,
                "The career center at this university was extremely helpful in finding me an internship and eventually a job after graduation."
        );

        PUReview review25 = new PUReview(
                3l,
                "The professors at this university were hit or miss. Some were excellent, but others seemed to be phoning it in."
        );

        PUReview review26 = new PUReview(
                4l,
                "The location of this university was perfect for me. It was close to everything I needed, and there was always something to do in the surrounding area."
        );

        PUReview review27 = new PUReview(
                4l,
                "I found the coursework at this university to be extremely challenging, but I felt well-prepared for my field after graduation."
        );

        PUReview review28 = new PUReview(
                2l,
                "The campus at this university was beautiful, but the administration was not very helpful or organized."
        );

        PUReview review29 = new PUReview(
                4l,
                "I had a great time participating in extracurricular activities at this university. They were well-organized and diverse."
        );

        PUReview review30 = new PUReview(
                4l,
                "The classes at this university were often overcrowded, and it was difficult to get the attention I needed from the professors."
        );

        PUReview review31 = new PUReview(
                4l,
                "The financial aid process at this university was straightforward and easy to navigate."
        );

        PUReview review32 = new PUReview(
                4l,
                "The facilities at this university were top-notch, and I always felt safe and comfortable on campus."
        );

        PUReview review33 = new PUReview(
                5l,
                "I had a great experience with the study abroad program at this university. The staff were extremely helpful and supportive."
        );

        PUReview review34 = new PUReview(
                2l,
                "The dorms at this university were pretty average, but the dining hall had some great options."
        );

        PUReview review35 = new PUReview(
                4l,
                "I found the coursework at this university to be challenging, but the professors were always available to help."
        );

        PUReview review36 = new PUReview(
                2l,
                "The campus at this university was not very diverse, and I sometimes felt out of place as a minority student."
        );

        PUReview review37 = new PUReview(
                3l,
                "The extracurricular activities at this university were not well-organized, and I didn't find many that interested me."
        );

        PUReview review38 = new PUReview(
                3l,
                "I had some issues with the administration at this university, but overall I enjoyed my experience and felt well-prepared for my career."
        );

        PUReview review39 = new PUReview(
                4l,
                "I had a great experience at this university. The professors were knowledgeable and supportive, and the campus was beautiful. However, the administrative processes could be more streamlined."
        );

        PUReview review40 = new PUReview(
                4l,
                "Overall, I had a positive experience at this university. The classes were interesting and the campus was nice, but the facilities could use some updating."
        );

        PUReview review41 = new PUReview(
                3l,
                "I was disappointed with my experience at this university. The professors seemed disinterested and the campus was outdated. However, the location was convenient."
        );

        PUReview review42 = new PUReview(
                5l,
                "This university provided me with an incredible education and a wealth of opportunities. The faculty and staff were dedicated and supportive, and the campus was modern and well-equipped."
        );

        PUReview review43 = new PUReview(
                2l,
                "I would not recommend this university. The professors were unresponsive and the facilities were run-down. Save your money and go elsewhere."
        );

        PUReview review44 = new PUReview(
                3l,
                "I loved attending this university. The classes were challenging but rewarding, and the campus was vibrant and diverse."
        );

        PUReview review45 = new PUReview(
                3l,
                "I was disappointed with my experience at this university. The facilities were outdated and the campus lacked a sense of community. However, the professors were knowledgeable."
        );

        PUReview review46 = new PUReview(
                5l,
                "This university exceeded my expectations in every way. The professors were inspiring and the facilities were state-of-the-art. I am so grateful for my time here."
        );

        PUReview review47 = new PUReview(
                4l,
                "My experience at this university was mixed. Some of the professors were great, but others were unengaged. The facilities were adequate but could use some updating."
        );

        PUReview review48 = new PUReview(
                4l,
                "This university provided me with an exceptional education and countless opportunities to get involved on campus. I would highly recommend it."
        );

        PUReview review49 = new PUReview(
                1l,
                "I had a terrible experience at this university. The professors were uninterested and the facilities were run-down. I regret my decision to attend."
        );

        PUReview review50 = new PUReview(
                4l,
                "Overall, my experience at this university was positive. The classes were challenging and the campus was nice, but some of the administrative processes were confusing."
        );

        PUReview review51 = new PUReview(
                5l,
                "I had a fantastic experience at this university. The professors were knowledgeable and engaging, and the campus was well-maintained. I am proud to be an alumnus."
        );

        PUReview review52 = new PUReview(
                2l,
                "I was not impressed with this university. The professors seemed uninterested and the facilities were outdated. I would recommend looking elsewhere."
        );

        PUReview review53 = new PUReview(
                4l,
                "This university provided me with a great education and plenty of opportunities to get involved on campus. The faculty and staff were supportive and encouraging."
        );

        PUReview review54 = new PUReview(
                4l,
                "My experience at this university was mixed. Some of the professors were excellent, but others were unimpressive. The campus was nice but could use some updating."
        );

        PUReview review55 = new PUReview(
                5l,
                "I absolutely loved attending this university. The professors were passionate and engaging, and the campus was full of opportunities to learn and grow."
        );

        PUReview review56 = new PUReview(
                3l,
                "I would not recommend this university. The professors seemed uninterested and the facilities were outdated. Save your money and go elsewhere."
        );

        PUReview review57 = new PUReview(
                4l,
                "I had a great experience studying at this university. The professors were knowledgeable and helpful, and the campus was beautiful."
        );

        PUReview review58 = new PUReview(
                4l,
                "The workload at this university was extremely heavy, but I learned a lot and felt well-prepared for my career."
        );

        PUReview review59 = new PUReview(
                4l,
                "I loved the sense of community at this university. There were always events and activities to participate in, and I made some great friends."
        );

        PUReview review60 = new PUReview(
                4l,
                "The dorms at this university were not great. They were small and poorly maintained, and the food in the dining hall was not very good."
        );

        PUReview review61 = new PUReview(
                5l,
                "I had a fantastic time studying abroad through this university's program. It was a life-changing experience."
        );

        PUReview review62 = new PUReview(
                5l,
                "The career center at this university was extremely helpful in finding me an internship and eventually a job after graduation."
        );

        PUReview review63 = new PUReview(
                4l,
                "The professors at this university were hit or miss. Some were excellent, but others seemed to be phoning it in."
        );

        PUReview review64 = new PUReview(
                4l,
                "The location of this university was perfect for me. It was close to everything I needed, and there was always something to do in the surrounding area."
        );

        PUReview review65 = new PUReview(
                3l,
                "I found the coursework at this university to be extremely challenging, but I felt well-prepared for my field after graduation."
        );

        PUReview review66 = new PUReview(
                2l,
                "The campus at this university was beautiful, but the administration was not very helpful or organized."
        );

        PUReview review67 = new PUReview(
                4l,
                "I had a great time participating in extracurricular activities at this university. They were well-organized and diverse."
        );

        PUReview review68 = new PUReview(
                4l,
                "The classes at this university were often overcrowded, and it was difficult to get the attention I needed from the professors."
        );

        PUReview review69 = new PUReview(
                4l,
                "The financial aid process at this university was straightforward and easy to navigate."
        );

        PUReview review70 = new PUReview(
                4l,
                "The facilities at this university were top-notch, and I always felt safe and comfortable on campus."
        );

        PUReview review71 = new PUReview(
                5l,
                "I had a great experience with the study abroad program at this university. The staff were extremely helpful and supportive."
        );

        PUReview review72 = new PUReview(
                2l,
                "The dorms at this university were pretty average, but the dining hall had some great options."
        );

        PUReview review73 = new PUReview(
                4l,
                "I found the coursework at this university to be challenging, but the professors were always available to help."
        );

        PUReview review74 = new PUReview(
                1l,
                "The campus at this university was not very diverse, and I sometimes felt out of place as a minority student."
        );

        PUReview review75 = new PUReview(
                2l,
                "The extracurricular activities at this university were not well-organized, and I didn't find many that interested me."
        );

        PUReview review76 = new PUReview(
                2l,
                "I had some issues with the administration at this university, but overall I enjoyed my experience and felt well-prepared for my career."
        );
        PUReview review77 = new PUReview(
                4l,
                "I loved attending this university. The classes were challenging but rewarding, and the campus was vibrant and diverse."
        );

        PUReview review78 = new PUReview(
                2l,
                "I was disappointed with my experience at this university. The facilities were outdated and the campus lacked a sense of community. However, the professors were knowledgeable."
        );

        PUReview review79 = new PUReview(
                5l,
                "This university exceeded my expectations in every way. The professors were inspiring and the facilities were state-of-the-art. I am so grateful for my time here."
        );

        PUReview review80 = new PUReview(
                3l,
                "My experience at this university was mixed. Some of the professors were great, but others were unengaged. The facilities were adequate but could use some updating."
        );

        PUReview review81 = new PUReview(
                4l,
                "This university provided me with an exceptional education and countless opportunities to get involved on campus. I would highly recommend it."
        );

        PUReview review82 = new PUReview(
                1l,
                "I had a terrible experience at this university. The professors were uninterested and the facilities were run-down. I regret my decision to attend."
        );

        PUReview review83 = new PUReview(
                3l,
                "Overall, my experience at this university was positive. The classes were challenging and the campus was nice, but some of the administrative processes were confusing."
        );

        PUReview review84 = new PUReview(
                5l,
                "I had a fantastic experience at this university. The professors were knowledgeable and engaging, and the campus was well-maintained. I am proud to be an alumnus."
        );

        PUReview review85 = new PUReview(
                2l,
                "I was not impressed with this university. The professors seemed uninterested and the facilities were outdated. I would recommend looking elsewhere."
        );

        PUReview review86 = new PUReview(
                4l,
                "This university provided me with a great education and plenty of opportunities to get involved on campus. The faculty and staff were supportive and encouraging."
        );

        PUReview review87 = new PUReview(
                3l,
                "My experience at this university was mixed. Some of the professors were excellent, but others were unimpressive. The campus was nice but could use some updating."
        );

        PUReview review88 = new PUReview(
                5l,
                "I absolutely loved attending this university. The professors were passionate and engaging, and the campus was full of opportunities to learn and grow."
        );

        PUReview review89 = new PUReview(
                2l,
                "I would not recommend this university. The professors seemed uninterested and the facilities were outdated. Save your money and go elsewhere."
        );

        PUReview review90 = new PUReview(
                3l,
                "I had a great experience studying at this university. The professors were knowledgeable and helpful, and the campus was beautiful."
        );

        PUReview review91 = new PUReview(
                3l,
                "The workload at this university was extremely heavy, but I learned a lot and felt well-prepared for my career."
        );

        PUReview review92 = new PUReview(
                1l,
                "I would not recommend this university. The professors were unresponsive and the facilities were run-down. Save your money and go elsewhere."
        );

        PUReview review93 = new PUReview(
                4l,
                "I loved attending this university. The classes were challenging but rewarding, and the campus was vibrant and diverse."
        );

        PUReview review94 = new PUReview(
                2l,
                "I was disappointed with my experience at this university. The facilities were outdated and the campus lacked a sense of community. However, the professors were knowledgeable."
        );

        PUReview review95 = new PUReview(
                5l,
                "This university exceeded my expectations in every way. The professors were inspiring and the facilities were state-of-the-art. I am so grateful for my time here."
        );

        PUReview review96 = new PUReview(
                3l,
                "My experience at this university was mixed. Some of the professors were great, but others were unengaged. The facilities were adequate but could use some updating."
        );

        PUReview review97 = new PUReview(
                4l,
                "This university provided me with an exceptional education and countless opportunities to get involved on campus. I would highly recommend it."
        );

        PUReview review98 = new PUReview(
                1l,
                "I had a terrible experience at this university. The professors were uninterested and the facilities were run-down. I regret my decision to attend."
        );

        PUReview review99 = new PUReview(
                3l,
                "Overall, my experience at this university was positive. The classes were challenging and the campus was nice, but some of the administrative processes were confusing."
        );

        PUReview review100 = new PUReview(
                5l,
                "I had a fantastic experience at this university. The professors were knowledgeable and engaging, and the campus was well-maintained. I am proud to be an alumnus."
        );

        PUReview review101 = new PUReview(
                2l,
                "I was not impressed with this university. The professors seemed uninterested and the facilities were outdated. I would recommend looking elsewhere."
        );

        PUReview review102 = new PUReview(
                4l,
                "This university provided me with a great education and plenty of opportunities to get involved on campus. The faculty and staff were supportive and encouraging."
        );

        PUReview review103 = new PUReview(
                3l,
                "My experience at this university was mixed. Some of the professors were excellent, but others were unimpressive. The campus was nice but could use some updating."
        );

        PUReview review104 = new PUReview(
                5l,
                "I absolutely loved attending this university. The professors were passionate and engaging, and the campus was full of opportunities to learn and grow."
        );

        PUReview review105 = new PUReview(
                2l,
                "I would not recommend this university. The professors seemed uninterested and the facilities were outdated. Save your money and go elsewhere."
        );

        PUReview review106 = new PUReview(
                3l,
                "I had a great experience studying at this university. The professors were knowledgeable and helpful, and the campus was beautiful."
        );

        PUReview review107 = new PUReview(
                3l,
                "The workload at this university was extremely heavy, but I learned a lot and felt well-prepared for my career."
        );

        PUReview review108 = new PUReview(
                2l,
                "I would not recommend this university. The professors seemed uninterested and the facilities were outdated. Save your money and go elsewhere."
        );

        PUReview review109 = new PUReview(
                4l,
                "I loved attending this university. The classes were challenging but rewarding, and the campus was vibrant and diverse."
        );

        PUReview review110 = new PUReview(
                2l,
                "I was disappointed with my experience at this university. The facilities were outdated and the campus lacked a sense of community. However, the professors were knowledgeable."
        );

        PUReview review111 = new PUReview(
                5l,
                "This university exceeded my expectations in every way. The professors were inspiring and the facilities were state-of-the-art. I am so grateful for my time here."
        );

        PUReview review112 = new PUReview(
                3l,
                "My experience at this university was mixed. Some of the professors were great, but others were unengaged. The facilities were adequate but could use some updating."
        );

        PUReview review113 = new PUReview(
                4l,
                "This university provided me with an exceptional education and countless opportunities to get involved on campus. I would highly recommend it."
        );

        PUReview review114 = new PUReview(
                1l,
                "I had a terrible experience at this university. The professors were uninterested and the facilities were run-down. I regret my decision to attend."
        );

        PUReview review115 = new PUReview(
                3l,
                "Overall, my experience at this university was positive. The classes were challenging and the campus was nice, but some of the administrative processes were confusing."
        );

        PUReview review116 = new PUReview(
                5l,
                "I had a fantastic experience at this university. The professors were knowledgeable and engaging, and the campus was well-maintained. I am proud to be an alumnus."
        );

        PUReview review117 = new PUReview(
                2l,
                "I was not impressed with this university. The professors seemed uninterested and the facilities were outdated. I would recommend looking elsewhere."
        );

        PUReview review118 = new PUReview(
                4l,
                "This university provided me with a great education and plenty of opportunities to get involved on campus. The faculty and staff were supportive and encouraging."
        );

        PUReview review119 = new PUReview(
                3l,
                "My experience at this university was mixed. Some of the professors were excellent, but others were unimpressive. The campus was nice but could use some updating."
        );

        PUReview review120 = new PUReview(
                5l,
                "I absolutely loved attending this university. The professors were passionate and engaging, and the campus was full of opportunities to learn and grow."
        );

        PUReview review121 = new PUReview(
                2l,
                "I would not recommend this university. The professors seemed uninterested and the facilities were outdated. Save your money and go elsewhere."
        );

        PUReview review122 = new PUReview(
                3l,
                "I had a great experience studying at this university. The professors were knowledgeable and helpful, and the campus was beautiful."
        );

        PUReview review123 = new PUReview(
                3l,
                "The workload at this university was extremely heavy, but I learned a lot and felt well-prepared for my career."
        );

        PUReview review124 = new PUReview(
                1l,
                "I would not recommend this university. The professors were unresponsive and the facilities were run-down. Save your money and go elsewhere."
        );

        PUReview review125 = new PUReview(
                4l,
                "I loved attending this university. The classes were challenging but rewarding, and the campus was vibrant and diverse."
        );

        PUReview review126 = new PUReview(
                2l,
                "I was disappointed with my experience at this university. The facilities were outdated and the campus lacked a sense of community. However, the professors were knowledgeable."
        );

        PUReview review127 = new PUReview(
                5l,
                "This university exceeded my expectations in every way. The professors were inspiring and the facilities were state-of-the-art. I am so grateful for my time here."
        );

        PUReview review128 = new PUReview(
                3l,
                "My experience at this university was mixed. Some of the professors were great, but others were unengaged. The facilities were adequate but could use some updating."
        );

        PUReview review129 = new PUReview(
                4l,
                "This university provided me with an exceptional education and countless opportunities to get involved on campus. I would highly recommend it."
        );

        PUReview review130 = new PUReview(
                1l,
                "I had a terrible experience at this university. The professors were uninterested and the facilities were run-down. I regret my decision to attend."
        );

        PUReview review131 = new PUReview(
                3l,
                "Overall, my experience at this university was positive. The classes were challenging and the campus was nice, but some of the administrative processes were confusing."
        );

        PUReview review132 = new PUReview(
                5l,
                "I had a fantastic experience at this university. The professors were knowledgeable and engaging, and the campus was well-maintained. I am proud to be an alumnus."
        );

        PUReview review133 = new PUReview(
                2l,
                "I was not impressed with this university. The professors seemed uninterested and the facilities were outdated. I would recommend looking elsewhere."
        );

        PUReview review134 = new PUReview(
                4l,
                "This university provided me with a great education and plenty of opportunities to get involved on campus. The faculty and staff were supportive and encouraging."
        );

        PUReview review135 = new PUReview(
                3l,
                "My experience at this university was mixed. Some of the professors were excellent, but others were unimpressive. The campus was nice but could use some updating."
        );

        PUReview review136 = new PUReview(
                5l,
                "I absolutely loved attending this university. The professors were passionate and engaging, and the campus was full of opportunities to learn and grow."
        );

        PUReview review137 = new PUReview(
                2l,
                "I would not recommend this university. The professors seemed uninterested and the facilities were outdated. Save your money and go elsewhere."
        );

        review1.setIsInappropriate(true);
        review2.setIsInappropriate(true);
        review3.setIsInappropriate(true);
        review4.setIsInappropriate(true);
        review5.setIsInappropriate(true);

        pUReviewSessionBean.createPUReview(review1, pId1, studentId1);
        pUReviewSessionBean.createPUReview(review2, pId1, studentId2);
        pUReviewSessionBean.createPUReview(review3, pId1, studentId3);
        pUReviewSessionBean.createPUReview(review4, pId1, studentId4);
        pUReviewSessionBean.createPUReview(review5, pId1, studentId5);
        pUReviewSessionBean.createPUReview(review6, pId2, studentId6);
        pUReviewSessionBean.createPUReview(review7, pId2, studentId7);
        pUReviewSessionBean.createPUReview(review8, pId2, studentId8);
        pUReviewSessionBean.createPUReview(review9, pId2, studentId9);
        pUReviewSessionBean.createPUReview(review10, pId2, studentId10);
        pUReviewSessionBean.createPUReview(review11, pId3, studentId11);
        pUReviewSessionBean.createPUReview(review12, pId3, studentId12);
        pUReviewSessionBean.createPUReview(review13, pId3, studentId13);
        pUReviewSessionBean.createPUReview(review14, pId3, studentId14);
        pUReviewSessionBean.createPUReview(review15, pId3, studentId15);
        pUReviewSessionBean.createPUReview(review16, pId4, studentId16);
        pUReviewSessionBean.createPUReview(review17, pId4, studentId17);
        pUReviewSessionBean.createPUReview(review18, pId4, studentId18);

        pUReviewSessionBean.createPUReview(review19, pId1, studentId19);
        pUReviewSessionBean.createPUReview(review20, pId1, studentId20);
        pUReviewSessionBean.createPUReview(review21, pId1, studentId21);
        pUReviewSessionBean.createPUReview(review22, pId1, studentId22);
        pUReviewSessionBean.createPUReview(review23, pId1, studentId23);
        pUReviewSessionBean.createPUReview(review24, pId2, studentId24);
        pUReviewSessionBean.createPUReview(review25, pId2, studentId25);
        pUReviewSessionBean.createPUReview(review26, pId2, studentId26);
        pUReviewSessionBean.createPUReview(review27, pId2, studentId27);
        pUReviewSessionBean.createPUReview(review28, pId2, studentId28);
        pUReviewSessionBean.createPUReview(review29, pId3, studentId29);
        pUReviewSessionBean.createPUReview(review30, pId3, studentId30);
        pUReviewSessionBean.createPUReview(review31, pId3, studentId31);
        pUReviewSessionBean.createPUReview(review32, pId3, studentId32);
        pUReviewSessionBean.createPUReview(review33, pId3, studentId33);
        pUReviewSessionBean.createPUReview(review34, pId4, studentId34);
        pUReviewSessionBean.createPUReview(review35, pId4, studentId35);
        pUReviewSessionBean.createPUReview(review36, pId4, studentId36);

//        pUReviewSessionBean.createPUReview(review1, pId50, studentId1);
//        pUReviewSessionBean.createPUReview(review2, pId50, studentId2);
//        pUReviewSessionBean.createPUReview(review3, pId50, studentId3);
//        pUReviewSessionBean.createPUReview(review4, pId49, studentId4);
//        pUReviewSessionBean.createPUReview(review5, pId49, studentId5);
//        pUReviewSessionBean.createPUReview(review6, pId49, studentId6);
//        pUReviewSessionBean.createPUReview(review7, pId48, studentId7);
//        pUReviewSessionBean.createPUReview(review8, pId48, studentId8);
//        pUReviewSessionBean.createPUReview(review9, pId48, studentId9);
//        pUReviewSessionBean.createPUReview(review10, pId47, studentId10);
//        pUReviewSessionBean.createPUReview(review11, pId47, studentId11);
//        pUReviewSessionBean.createPUReview(review12, pId47, studentId12);
//        pUReviewSessionBean.createPUReview(review13, pId46, studentId13);
//        pUReviewSessionBean.createPUReview(review14, pId46, studentId14);
//        pUReviewSessionBean.createPUReview(review15, pId46, studentId15);
//        pUReviewSessionBean.createPUReview(review16, pId45, studentId16);
//        pUReviewSessionBean.createPUReview(review17, pId45, studentId17);
//        pUReviewSessionBean.createPUReview(review18, pId45, studentId18);
//
//        pUReviewSessionBean.createPUReview(review19, pId44, studentId19);
//        pUReviewSessionBean.createPUReview(review20, pId44, studentId20);
//        pUReviewSessionBean.createPUReview(review21, pId44, studentId21);
//        pUReviewSessionBean.createPUReview(review22, pId43, studentId22);
//        pUReviewSessionBean.createPUReview(review23, pId43, studentId23);
//        pUReviewSessionBean.createPUReview(review24, pId43, studentId24);
//        pUReviewSessionBean.createPUReview(review25, pId42, studentId25);
//        pUReviewSessionBean.createPUReview(review26, pId42, studentId26);
//        pUReviewSessionBean.createPUReview(review27, pId42, studentId27);
//        pUReviewSessionBean.createPUReview(review28, pId41, studentId28);
//        pUReviewSessionBean.createPUReview(review29, pId41, studentId29);
//        pUReviewSessionBean.createPUReview(review30, pId41, studentId30);
//        pUReviewSessionBean.createPUReview(review31, pId40, studentId31);
//        pUReviewSessionBean.createPUReview(review32, pId40, studentId32);
//        pUReviewSessionBean.createPUReview(review33, pId40, studentId33);
//        pUReviewSessionBean.createPUReview(review34, pId39, studentId34);
//        pUReviewSessionBean.createPUReview(review35, pId39, studentId35);
//        pUReviewSessionBean.createPUReview(review36, pId39, studentId36);
//
//        pUReviewSessionBean.createPUReview(review36, pId27, studentId36);
        pUReviewSessionBean.createPUReview(review37, pId27, studentId37);
        pUReviewSessionBean.createPUReview(review38, pId27, studentId38);
        pUReviewSessionBean.createPUReview(review39, pId27, studentId39);
        pUReviewSessionBean.createPUReview(review40, pId28, studentId40);
        pUReviewSessionBean.createPUReview(review41, pId28, studentId41);
        pUReviewSessionBean.createPUReview(review42, pId28, studentId42);
        pUReviewSessionBean.createPUReview(review43, pId29, studentId43);
        pUReviewSessionBean.createPUReview(review44, pId29, studentId44);
        pUReviewSessionBean.createPUReview(review45, pId29, studentId45);
        pUReviewSessionBean.createPUReview(review46, pId30, studentId46);
        pUReviewSessionBean.createPUReview(review47, pId30, studentId47);
        pUReviewSessionBean.createPUReview(review48, pId30, studentId48);
        pUReviewSessionBean.createPUReview(review49, pId31, studentId49);
        pUReviewSessionBean.createPUReview(review50, pId31, studentId50);
        pUReviewSessionBean.createPUReview(review51, pId31, studentId51);
        pUReviewSessionBean.createPUReview(review52, pId32, studentId52);
        pUReviewSessionBean.createPUReview(review53, pId32, studentId53);
        pUReviewSessionBean.createPUReview(review54, pId32, studentId54);
        pUReviewSessionBean.createPUReview(review55, pId33, studentId55);
        pUReviewSessionBean.createPUReview(review56, pId33, studentId56);
        pUReviewSessionBean.createPUReview(review57, pId33, studentId57);
        pUReviewSessionBean.createPUReview(review58, pId34, studentId58);
        pUReviewSessionBean.createPUReview(review59, pId34, studentId59);
        pUReviewSessionBean.createPUReview(review60, pId34, studentId60);
        pUReviewSessionBean.createPUReview(review61, pId35, studentId61);
        pUReviewSessionBean.createPUReview(review62, pId35, studentId62);
        pUReviewSessionBean.createPUReview(review63, pId35, studentId63);
        pUReviewSessionBean.createPUReview(review64, pId36, studentId64);
        pUReviewSessionBean.createPUReview(review65, pId36, studentId65);
        pUReviewSessionBean.createPUReview(review66, pId36, studentId66);
        pUReviewSessionBean.createPUReview(review67, pId37, studentId67);
        pUReviewSessionBean.createPUReview(review68, pId37, studentId68);
        pUReviewSessionBean.createPUReview(review69, pId37, studentId69);
        pUReviewSessionBean.createPUReview(review70, pId38, studentId70);
        pUReviewSessionBean.createPUReview(review71, pId38, studentId71);
        pUReviewSessionBean.createPUReview(review72, pId38, studentId72);

        pUReviewSessionBean.createPUReview(review73, pId26, studentId73);
        pUReviewSessionBean.createPUReview(review74, pId26, studentId74);
        pUReviewSessionBean.createPUReview(review75, pId26, studentId75);
        pUReviewSessionBean.createPUReview(review76, pId25, studentId76);
        pUReviewSessionBean.createPUReview(review77, pId25, studentId77);
        pUReviewSessionBean.createPUReview(review78, pId25, studentId78);
        pUReviewSessionBean.createPUReview(review79, pId24, studentId79);
        pUReviewSessionBean.createPUReview(review80, pId24, studentId80);
        pUReviewSessionBean.createPUReview(review81, pId24, studentId81);
        pUReviewSessionBean.createPUReview(review82, pId23, studentId82);
        pUReviewSessionBean.createPUReview(review83, pId23, studentId83);
        pUReviewSessionBean.createPUReview(review84, pId23, studentId84);
        pUReviewSessionBean.createPUReview(review85, pId22, studentId85);
        pUReviewSessionBean.createPUReview(review86, pId22, studentId86);
        pUReviewSessionBean.createPUReview(review87, pId22, studentId87);
        pUReviewSessionBean.createPUReview(review88, pId21, studentId88);
        pUReviewSessionBean.createPUReview(review89, pId21, studentId89);
        pUReviewSessionBean.createPUReview(review90, pId21, studentId90);

        pUReviewSessionBean.createPUReview(review91, pId20, studentId91);
        pUReviewSessionBean.createPUReview(review92, pId20, studentId92);
        pUReviewSessionBean.createPUReview(review93, pId20, studentId93);
        pUReviewSessionBean.createPUReview(review94, pId19, studentId94);
        pUReviewSessionBean.createPUReview(review95, pId19, studentId95);
        pUReviewSessionBean.createPUReview(review96, pId19, studentId96);
        pUReviewSessionBean.createPUReview(review97, pId18, studentId97);
        pUReviewSessionBean.createPUReview(review98, pId18, studentId98);
        pUReviewSessionBean.createPUReview(review99, pId18, studentId99);
        pUReviewSessionBean.createPUReview(review100, pId17, studentId100);
        pUReviewSessionBean.createPUReview(review101, pId17, studentId101);
        pUReviewSessionBean.createPUReview(review102, pId17, studentId102);
        pUReviewSessionBean.createPUReview(review103, pId16, studentId103);
        pUReviewSessionBean.createPUReview(review104, pId16, studentId104);
        pUReviewSessionBean.createPUReview(review105, pId16, studentId105);
        pUReviewSessionBean.createPUReview(review106, pId15, studentId106);
        pUReviewSessionBean.createPUReview(review107, pId15, studentId107);
        pUReviewSessionBean.createPUReview(review108, pId15, studentId108);

        pUReviewSessionBean.createPUReview(review109, pId5, studentId109);
        pUReviewSessionBean.createPUReview(review110, pId5, studentId110);
        pUReviewSessionBean.createPUReview(review111, pId6, studentId111);
        pUReviewSessionBean.createPUReview(review112, pId6, studentId112);
        pUReviewSessionBean.createPUReview(review113, pId6, studentId113);
        pUReviewSessionBean.createPUReview(review114, pId7, studentId114);
        pUReviewSessionBean.createPUReview(review115, pId7, studentId115);
        pUReviewSessionBean.createPUReview(review116, pId7, studentId116);
        pUReviewSessionBean.createPUReview(review117, pId8, studentId117);
        pUReviewSessionBean.createPUReview(review118, pId8, studentId118);
        pUReviewSessionBean.createPUReview(review119, pId8, studentId119);
        pUReviewSessionBean.createPUReview(review120, pId9, studentId120);
        pUReviewSessionBean.createPUReview(review121, pId9, studentId121);
        pUReviewSessionBean.createPUReview(review122, pId10, studentId122);
        pUReviewSessionBean.createPUReview(review123, pId10, studentId123);
        pUReviewSessionBean.createPUReview(review124, pId10, studentId124);
        pUReviewSessionBean.createPUReview(review125, pId11, studentId125);
        pUReviewSessionBean.createPUReview(review126, pId11, studentId126);
        pUReviewSessionBean.createPUReview(review127, pId11, studentId127);
        pUReviewSessionBean.createPUReview(review128, pId12, studentId128);
        pUReviewSessionBean.createPUReview(review129, pId12, studentId129);
        pUReviewSessionBean.createPUReview(review130, pId12, studentId130);
        pUReviewSessionBean.createPUReview(review131, pId12, studentId131);
        pUReviewSessionBean.createPUReview(review132, pId13, studentId132);
        pUReviewSessionBean.createPUReview(review133, pId13, studentId133);
        pUReviewSessionBean.createPUReview(review134, pId13, studentId134);
        pUReviewSessionBean.createPUReview(review135, pId14, studentId135);
        pUReviewSessionBean.createPUReview(review136, pId14, studentId136);
        pUReviewSessionBean.createPUReview(review137, pId14, studentId137);

        pUSessionBean.enrollStudent(pId1, studentId1);
        pUSessionBean.enrollStudent(pId1, studentId2);
        pUSessionBean.enrollStudent(pId1, studentId3);
        pUSessionBean.enrollStudent(pId1, studentId4);
        pUSessionBean.enrollStudent(pId1, studentId5);
        pUSessionBean.enrollStudent(pId2, studentId6);
        pUSessionBean.enrollStudent(pId2, studentId7);
        pUSessionBean.enrollStudent(pId2, studentId8);
        pUSessionBean.enrollStudent(pId2, studentId9);
        pUSessionBean.enrollStudent(pId2, studentId10);
        pUSessionBean.enrollStudent(pId3, studentId11);
        pUSessionBean.enrollStudent(pId3, studentId12);
        pUSessionBean.enrollStudent(pId3, studentId13);
        pUSessionBean.enrollStudent(pId3, studentId14);
        pUSessionBean.enrollStudent(pId3, studentId15);
        pUSessionBean.enrollStudent(pId4, studentId16);
        pUSessionBean.enrollStudent(pId4, studentId17);
        pUSessionBean.enrollStudent(pId4, studentId18);

        pUSessionBean.enrollStudent(pId1, studentId19);
        pUSessionBean.enrollStudent(pId1, studentId20);
        pUSessionBean.enrollStudent(pId1, studentId21);
        pUSessionBean.enrollStudent(pId1, studentId22);
        pUSessionBean.enrollStudent(pId1, studentId23);
        pUSessionBean.enrollStudent(pId2, studentId24);
        pUSessionBean.enrollStudent(pId2, studentId25);
        pUSessionBean.enrollStudent(pId2, studentId26);
        pUSessionBean.enrollStudent(pId2, studentId27);
        pUSessionBean.enrollStudent(pId2, studentId28);
        pUSessionBean.enrollStudent(pId3, studentId29);
        pUSessionBean.enrollStudent(pId3, studentId30);
        pUSessionBean.enrollStudent(pId3, studentId31);
        pUSessionBean.enrollStudent(pId3, studentId32);
        pUSessionBean.enrollStudent(pId3, studentId33);
        pUSessionBean.enrollStudent(pId4, studentId34);
        pUSessionBean.enrollStudent(pId4, studentId35);
        pUSessionBean.enrollStudent(pId4, studentId36);

        PUModule module1 = new PUModule(
                "Introduction to Programming",
                "CS101",
                "This module covers the basics of programming with Java."
        );
        PUModule module2 = new PUModule(
                "Data Structures and Algorithms",
                "CS201",
                "This module explores various data structures and algorithms used in programming."
        );
        PUModule module3 = new PUModule(
                "Database Systems",
                "CS301",
                "This module covers the design and implementation of database systems."
        );
        PUModule module4 = new PUModule(
                "Artificial Intelligence",
                "CS401",
                "This module covers the fundamentals of artificial intelligence and machine learning."
        );
        PUModule module5 = new PUModule(
                "Computer Networks",
                "CS501",
                "This module covers the concepts and protocols used in computer networks."
        );
        PUModule module6 = new PUModule(
                "Software Engineering",
                "CS601",
                "This module covers the various stages of software development and project management."
        );
        PUModule module7 = new PUModule(
                "Web Development",
                "CS701",
                "This module covers the development of web applications using HTML, CSS, and JavaScript."
        );
        PUModule module8 = new PUModule(
                "Computer Graphics",
                "CS801",
                "This module covers the principles and techniques of computer graphics and visualisation."
        );
        PUModule module9 = new PUModule(
                "Operating Systems",
                "CS901",
                "This module covers the design and implementation of operating systems."
        );
        PUModule module10 = new PUModule(
                "Parallel and Distributed Computing",
                "CS1001",
                "This module covers the fundamentals of parallel and distributed computing."
        );

        PUModule module11 = new PUModule(
                "Introduction to History",
                "HIS101",
                "An introduction to the study of history and its key concepts and methods."
        );
        PUModule module12 = new PUModule(
                "Philosophy of Art",
                "ART102",
                "An examination of the major philosophical debates surrounding art and aesthetics."
        );
        PUModule module13 = new PUModule(
                "Introduction to Linguistics",
                "LIN101",
                "An introduction to the study of language and its structure and use."
        );
        PUModule module14 = new PUModule(
                "Creative Writing",
                "ENG102",
                "An exploration of creative writing techniques and styles."
        );
        PUModule module15 = new PUModule(
                "Sociology of Health and Illness",
                "SOC102",
                "An examination of the social factors that shape health and illness."
        );
        PUModule module16 = new PUModule(
                "Environmental Studies",
                "ENV101",
                "An exploration of the relationship between humans and the environment."
        );
        PUModule module17 = new PUModule(
                "Philosophy of Mind",
                "PHI101",
                "An introduction to the major theories and debates in the philosophy of mind."
        );
        PUModule module18 = new PUModule(
                "History of Music",
                "MUS101",
                "An introduction to the history of music and its major composers and styles."
        );
        PUModule module19 = new PUModule(
                "Global Politics",
                "POL102",
                "An examination of the major political issues and actors on the global stage."
        );
        PUModule module20 = new PUModule(
                "Religious Studies",
                "REL101",
                "An exploration of the role of religion in society and its impact on culture and politics."
        );

        PUModule module21 = new PUModule(
                "Marketing Management",
                "BUS101",
                "Introduction to marketing principles and practices."
        );
        PUModule module22 = new PUModule(
                "Corporate Finance",
                "BUS102",
                "Study of financial decision making in corporations."
        );
        PUModule module23 = new PUModule(
                "Organizational Behavior",
                "BUS103",
                "An examination of individual, group and organizational behavior in the workplace."
        );
        PUModule module24 = new PUModule(
                "Operations Management",
                "BUS104",
                "Introduction to the management of production and operations."
        );
        PUModule module25 = new PUModule(
                "Business Ethics",
                "BUS105",
                "Examines ethical issues in business, including corporate social responsibility."
        );
        PUModule module26 = new PUModule(
                "International Business",
                "BUS106",
                "Explores the international business environment and its effects on business operations."
        );
        PUModule module27 = new PUModule(
                "Business Law",
                "BUS107",
                "Provides an understanding of the legal aspects of business transactions."
        );
        PUModule module28 = new PUModule(
                "Entrepreneurship",
                "BUS108",
                "Covers the basic principles and practices of entrepreneurship."
        );
        PUModule module29 = new PUModule(
                "Human Resource Management",
                "BUS109",
                "An overview of human resource management principles and practices."
        );
        PUModule module30 = new PUModule(
                "Economics for Business",
                "BUS110",
                "An introduction to microeconomics and macroeconomics for business decision making."
        );

        PUModule module31 = new PUModule(
                "Dental Anatomy and Occlusion",
                "DEN201",
                "This module covers the anatomy of the teeth and jaws, as well as occlusion and the principles of dental occlusion."
        );
        PUModule module32 = new PUModule(
                "Dental Pharmacology and Therapeutics",
                "DEN202",
                "This module covers the pharmacology and therapeutics of drugs used in dental practice, including local and general anaesthetics, analgesics, and antibiotics."
        );
        PUModule module33 = new PUModule(
                "Oral Microbiology and Immunology",
                "DEN301",
                "This module covers the microbiology and immunology of the oral cavity, including the normal flora, opportunistic pathogens, and the host response to infection."
        );
        PUModule module34 = new PUModule(
                "Oral Medicine and Pathology",
                "DEN302",
                "This module covers the diagnosis, management, and treatment of oral diseases, including oral cancer, salivary gland disorders, and oral infections."
        );
        PUModule module35 = new PUModule(
                "Oral Radiology and Imaging",
                "DEN401",
                "This module covers the principles and techniques of dental radiology and imaging, including intraoral and extraoral radiographs, panoramic radiography, and cone beam computed tomography."
        );
        PUModule module36 = new PUModule(
                "Restorative Dentistry",
                "DEN402",
                "This module covers the principles and techniques of restorative dentistry, including the use of direct and indirect restorations, dental materials, and the management of dental caries."
        );
        PUModule module37 = new PUModule(
                "Endodontics",
                "DEN501",
                "This module covers the diagnosis and management of pulpal and periapical diseases, including root canal therapy, apexification, and apexogenesis."
        );
        PUModule module38 = new PUModule(
                "Periodontics",
                "DEN502",
                "This module covers the diagnosis and management of periodontal diseases, including gingivitis, periodontitis, and periodontal surgery."
        );
        PUModule module39 = new PUModule(
                "Prosthodontics",
                "DEN601",
                "This module covers the principles and techniques of prosthodontics, including the use of complete and partial dentures, fixed prosthodontics, and implant dentistry."
        );

        PUModule module40 = new PUModule(
                "Sustainable Building Design",
                "DES101",
                "Introduction to sustainable design principles for buildings."
        );
        PUModule module41 = new PUModule(
                "Environmental Design",
                "DES102",
                "Examination of the impact of the built environment on human health and well-being."
        );
        PUModule module42 = new PUModule(
                "Landscape Architecture",
                "DES103",
                "Introduction to the principles and practices of landscape architecture."
        );
        PUModule module43 = new PUModule(
                "Urban Planning",
                "DES104",
                "Overview of urban planning concepts, policies, and practices."
        );
        PUModule module44 = new PUModule(
                "Design Thinking",
                "DES105",
                "Introduction to the design thinking process and its applications in various fields."
        );
        PUModule module45 = new PUModule(
                "Architecture and Society",
                "DES106",
                "Exploration of the social and cultural contexts of architecture and the built environment."
        );
        PUModule module46 = new PUModule(
                "Sustainable Urbanism",
                "DES107",
                "Examination of sustainable urban design practices and strategies."
        );
        PUModule module47 = new PUModule(
                "Building Performance Analysis",
                "DES108",
                "Introduction to performance analysis tools for buildings."
        );
        PUModule module48 = new PUModule(
                "Design and Technology",
                "DES109",
                "Exploration of the relationship between design and technology in various fields."
        );
        PUModule module49 = new PUModule(
                "Design Research Methods",
                "DES110",
                "Introduction to research methods for design and its applications in various fields."
        );

        PUModule module50 = new PUModule(
                "Introduction to Computer Science",
                "ENG1001",
                "This module introduces students to the basic concepts and techniques of computer science, including programming languages, algorithms, data structures, and computer systems."
        );
        PUModule module51 = new PUModule(
                "Digital Electronics",
                "ENG1002",
                "This module covers the principles and applications of digital electronics, including Boolean algebra, combinational and sequential circuits, and memory devices."
        );
        PUModule module52 = new PUModule(
                "Engineering Mathematics I",
                "ENG1003",
                "This module provides an introduction to the fundamental concepts of mathematics used in engineering, including calculus, linear algebra, and differential equations."
        );
        PUModule module53 = new PUModule(
                "Mechanics and Materials",
                "ENG1004",
                "This module covers the basic principles of mechanics and materials science, including forces, moments, stress, strain, and deformation."
        );
        PUModule module54 = new PUModule(
                "Engineering Design and Innovation",
                "ENG1005",
                "This module introduces students to the process of engineering design and innovation, including problem identification, concept generation, prototyping, and evaluation."
        );
        PUModule module55 = new PUModule(
                "Signals and Systems",
                "ENG1006",
                "This module covers the fundamental concepts and techniques of signals and systems, including Fourier series and transforms, Laplace transforms, and system analysis."
        );
        PUModule module56 = new PUModule(
                "Engineering Mathematics II",
                "ENG1007",
                "This module builds upon the mathematical concepts covered in Engineering Mathematics I, including vector calculus, Fourier analysis, and partial differential equations."
        );
        PUModule module57 = new PUModule(
                "Circuits and Devices",
                "ENG1008",
                "This module covers the principles and applications of circuits and devices, including amplifiers, filters, oscillators, and transistors."
        );
        PUModule module58 = new PUModule(
                "Thermodynamics and Heat Transfer",
                "ENG1009",
                "This module introduces students to the principles and applications of thermodynamics and heat transfer, including laws of thermodynamics, energy conversion, and heat transfer mechanisms."
        );
        PUModule module59 = new PUModule(
                "Materials Science and Engineering",
                "ENG1010",
                "This module covers the fundamental principles of materials science and engineering, including crystal structure, defects, properties, and applications."
        );

        PUModule module60 = new PUModule(
                "Criminal Law",
                "LAW101",
                "This module provides an introduction to criminal law and its foundational concepts."
        );
        PUModule module61 = new PUModule(
                "Tort Law",
                "LAW102",
                "This module covers the law of torts and its practical application in various contexts."
        );
        PUModule module62 = new PUModule(
                "Contract Law",
                "LAW103",
                "This module explores the law of contracts and its role in shaping commercial transactions."
        );
        PUModule module63 = new PUModule(
                "Property Law",
                "LAW104",
                "This module provides an overview of property law and its historical development."
        );
        PUModule module64 = new PUModule(
                "Constitutional Law",
                "LAW105",
                "This module covers the principles of constitutional law and their application in legal practice."
        );
        PUModule module65 = new PUModule(
                "Legal Writing and Research",
                "LAW106",
                "This module teaches the skills required for legal research and writing."
        );
        PUModule module66 = new PUModule(
                "Human Rights Law",
                "LAW107",
                "This module examines the legal framework for human rights and its impact on contemporary issues."
        );
        PUModule module67 = new PUModule(
                "Intellectual Property Law",
                "LAW108",
                "This module explores the principles of intellectual property law and their application in practice."
        );
        PUModule module68 = new PUModule(
                "International Law",
                "LAW109",
                "This module provides an introduction to international law and its role in shaping global relations."
        );
        PUModule module69 = new PUModule(
                "Corporate Law",
                "LAW110",
                "This module covers the law of corporations and their governance."
        );

        PUModule module70 = new PUModule(
                "Human Anatomy",
                "MED101",
                "This module provides an in-depth understanding of the human anatomy, covering various systems and organs in the body."
        );
        PUModule module71 = new PUModule(
                "Pathophysiology",
                "MED102",
                "This module covers the pathophysiological basis of common diseases and their diagnosis, treatment and management."
        );
        PUModule module72 = new PUModule(
                "Medical Ethics",
                "MED103",
                "This module explores ethical issues in medicine, such as patient autonomy, informed consent, confidentiality, and end-of-life care."
        );
        PUModule module73 = new PUModule(
                "Pharmacology",
                "MED104",
                "This module covers the principles of pharmacology and the actions and uses of drugs in the treatment of various diseases."
        );
        PUModule module74 = new PUModule(
                "Medical Microbiology",
                "MED105",
                "This module provides an understanding of the biology of microorganisms and their role in infectious diseases."
        );
        PUModule module75 = new PUModule(
                "Medical Imaging",
                "MED106",
                "This module covers the principles of medical imaging and its application in diagnosis and treatment of diseases."
        );
        PUModule module76 = new PUModule(
                "Clinical Skills",
                "MED107",
                "This module focuses on the development of clinical skills in history taking, physical examination, and communication with patients."
        );
        PUModule module77 = new PUModule(
                "Community Medicine",
                "MED108",
                "This module covers the principles of community medicine and public health, including disease prevention and health promotion."
        );
        PUModule module78 = new PUModule(
                "Clinical Epidemiology",
                "MED109",
                "This module covers the principles of clinical epidemiology and research methodology, including study design and data analysis."
        );
        PUModule module79 = new PUModule(
                "Surgery",
                "MED110",
                "This module provides an introduction to surgical principles and techniques, including basic surgical skills and procedures."
        );

        PUModule module80 = new PUModule(
                "Machine Learning for Science",
                "SC9101",
                "This module introduces machine learning techniques for analyzing scientific data, including classification, clustering, and regression."
        );
        PUModule module81 = new PUModule(
                "Introduction to Genetics",
                "SC1101",
                "This module introduces students to the basic principles of genetics and how they apply to various fields of science."
        );
        PUModule module82 = new PUModule(
                "Environmental Science",
                "SC2201",
                "This module covers the fundamental concepts of environmental science and how they relate to current environmental issues."
        );
        PUModule module83 = new PUModule(
                "Biostatistics",
                "SC3101",
                "This module provides students with a foundation in statistical methods for biological research."
        );
        PUModule module84 = new PUModule(
                "Astrophysics",
                "SC4201",
                "This module explores the physical principles that govern the behavior of the universe, from stars and galaxies to black holes and dark matter."
        );
        PUModule module85 = new PUModule(
                "Computational Biology",
                "SC5101",
                "This module introduces students to computational methods for analyzing biological data, including gene sequencing and protein structure prediction."
        );
        PUModule module86 = new PUModule(
                "Nanotechnology",
                "SC6201",
                "This module explores the unique properties of materials at the nanoscale and their potential applications in fields such as electronics, medicine, and energy."
        );
        PUModule module87 = new PUModule(
                "Environmental Chemistry",
                "SC7201",
                "This module covers the chemistry of natural and anthropogenic processes that affect the environment, including atmospheric chemistry and water pollution."
        );
        PUModule module88 = new PUModule(
                "Quantum Mechanics",
                "SC8201",
                "This module provides an introduction to the principles of quantum mechanics, including wave-particle duality, uncertainty, and entanglement."
        );

        Long puModId1 = puModuleSessionBean.createPUModule(module1, pId1);
        Long puModId2 = puModuleSessionBean.createPUModule(module2, pId3);
        Long puModId3 = puModuleSessionBean.createPUModule(module3, pId2);
        Long puModId4 = puModuleSessionBean.createPUModule(module4, pId1);
        Long puModId5 = puModuleSessionBean.createPUModule(module5, pId4);
        Long puModId6 = puModuleSessionBean.createPUModule(module6, pId1);
        Long puModId7 = puModuleSessionBean.createPUModule(module7, pId1);
        Long puModId8 = puModuleSessionBean.createPUModule(module8, pId3);
        Long puModId9 = puModuleSessionBean.createPUModule(module9, pId2);
        Long puModId10 = puModuleSessionBean.createPUModule(module10, pId1);
        Long puModId11 = puModuleSessionBean.createPUModule(module11, pId4);
        Long puModId12 = puModuleSessionBean.createPUModule(module12, pId1);
        Long puModId13 = puModuleSessionBean.createPUModule(module13, pId1);
        Long puModId14 = puModuleSessionBean.createPUModule(module14, pId3);
        Long puModId15 = puModuleSessionBean.createPUModule(module15, pId2);
        Long puModId16 = puModuleSessionBean.createPUModule(module16, pId1);
        Long puModId17 = puModuleSessionBean.createPUModule(module17, pId4);
        Long puModId18 = puModuleSessionBean.createPUModule(module18, pId1);
        Long puModId19 = puModuleSessionBean.createPUModule(module19, pId1);
        Long puModId20 = puModuleSessionBean.createPUModule(module20, pId3);
        Long puModId21 = puModuleSessionBean.createPUModule(module21, pId2);
        Long puModId22 = puModuleSessionBean.createPUModule(module22, pId1);
        Long puModId23 = puModuleSessionBean.createPUModule(module23, pId4);
        Long puModId24 = puModuleSessionBean.createPUModule(module24, pId1);
        Long puModId25 = puModuleSessionBean.createPUModule(module25, pId1);
        Long puModId26 = puModuleSessionBean.createPUModule(module26, pId3);
        Long puModId27 = puModuleSessionBean.createPUModule(module27, pId2);
        Long puModId28 = puModuleSessionBean.createPUModule(module28, pId1);
        Long puModId29 = puModuleSessionBean.createPUModule(module29, pId4);
        Long puModId30 = puModuleSessionBean.createPUModule(module30, pId1);
        Long puModId31 = puModuleSessionBean.createPUModule(module31, pId1);
        Long puModId32 = puModuleSessionBean.createPUModule(module32, pId3);
        Long puModId33 = puModuleSessionBean.createPUModule(module33, pId2);
        Long puModId34 = puModuleSessionBean.createPUModule(module34, pId1);
        Long puModId35 = puModuleSessionBean.createPUModule(module35, pId4);
        Long puModId36 = puModuleSessionBean.createPUModule(module36, pId1);
        Long puModId37 = puModuleSessionBean.createPUModule(module37, pId1);
        Long puModId38 = puModuleSessionBean.createPUModule(module38, pId3);
        Long puModId39 = puModuleSessionBean.createPUModule(module39, pId2);
        Long puModId40 = puModuleSessionBean.createPUModule(module40, pId1);
        Long puModId41 = puModuleSessionBean.createPUModule(module41, pId4);
        Long puModId42 = puModuleSessionBean.createPUModule(module42, pId1);
        Long puModId43 = puModuleSessionBean.createPUModule(module43, pId1);
        Long puModId44 = puModuleSessionBean.createPUModule(module44, pId3);
        Long puModId45 = puModuleSessionBean.createPUModule(module45, pId2);
        Long puModId46 = puModuleSessionBean.createPUModule(module46, pId1);
        Long puModId47 = puModuleSessionBean.createPUModule(module47, pId4);
        Long puModId48 = puModuleSessionBean.createPUModule(module48, pId1);
        Long puModId49 = puModuleSessionBean.createPUModule(module49, pId1);
        Long puModId50 = puModuleSessionBean.createPUModule(module50, pId3);
        Long puModId51 = puModuleSessionBean.createPUModule(module51, pId2);
        Long puModId52 = puModuleSessionBean.createPUModule(module52, pId1);
        Long puModId53 = puModuleSessionBean.createPUModule(module53, pId4);
        Long puModId54 = puModuleSessionBean.createPUModule(module54, pId1);
        Long puModId55 = puModuleSessionBean.createPUModule(module55, pId1);
        Long puModId56 = puModuleSessionBean.createPUModule(module56, pId3);
        Long puModId57 = puModuleSessionBean.createPUModule(module57, pId2);
        Long puModId58 = puModuleSessionBean.createPUModule(module58, pId1);
        Long puModId59 = puModuleSessionBean.createPUModule(module59, pId4);
        Long puModId60 = puModuleSessionBean.createPUModule(module60, pId1);
        Long puModId61 = puModuleSessionBean.createPUModule(module61, pId1);
        Long puModId62 = puModuleSessionBean.createPUModule(module62, pId3);
        Long puModId63 = puModuleSessionBean.createPUModule(module63, pId2);
        Long puModId64 = puModuleSessionBean.createPUModule(module64, pId1);
        Long puModId65 = puModuleSessionBean.createPUModule(module65, pId4);
        Long puModId66 = puModuleSessionBean.createPUModule(module66, pId1);
        Long puModId67 = puModuleSessionBean.createPUModule(module67, pId1);
        Long puModId68 = puModuleSessionBean.createPUModule(module68, pId3);
        Long puModId69 = puModuleSessionBean.createPUModule(module69, pId2);
        Long puModId70 = puModuleSessionBean.createPUModule(module70, pId1);
        Long puModId71 = puModuleSessionBean.createPUModule(module71, pId4);
        Long puModId72 = puModuleSessionBean.createPUModule(module72, pId1);
        Long puModId73 = puModuleSessionBean.createPUModule(module73, pId1);
        Long puModId74 = puModuleSessionBean.createPUModule(module74, pId3);
        Long puModId75 = puModuleSessionBean.createPUModule(module75, pId2);
        Long puModId76 = puModuleSessionBean.createPUModule(module76, pId1);
        Long puModId77 = puModuleSessionBean.createPUModule(module77, pId4);
        Long puModId78 = puModuleSessionBean.createPUModule(module78, pId1);
        Long puModId79 = puModuleSessionBean.createPUModule(module79, pId1);
        Long puModId80 = puModuleSessionBean.createPUModule(module80, pId3);
        Long puModId81 = puModuleSessionBean.createPUModule(module81, pId2);
        Long puModId82 = puModuleSessionBean.createPUModule(module82, pId1);
        Long puModId83 = puModuleSessionBean.createPUModule(module83, pId4);
        Long puModId84 = puModuleSessionBean.createPUModule(module84, pId1);
        Long puModId85 = puModuleSessionBean.createPUModule(module85, pId1);
        Long puModId86 = puModuleSessionBean.createPUModule(module86, pId3);
        Long puModId87 = puModuleSessionBean.createPUModule(module87, pId2);
        Long puModId88 = puModuleSessionBean.createPUModule(module88, pId1);

        puModuleSessionBean.associatePUModuleNUSModule(puModId1, nusModId1);
        puModuleSessionBean.associatePUModuleNUSModule(puModId2, nusModId2);
        puModuleSessionBean.associatePUModuleNUSModule(puModId3, nusModId3);
        puModuleSessionBean.associatePUModuleNUSModule(puModId4, nusModId4);
        puModuleSessionBean.associatePUModuleNUSModule(puModId5, nusModId5);
        puModuleSessionBean.associatePUModuleNUSModule(puModId6, nusModId6);
        puModuleSessionBean.associatePUModuleNUSModule(puModId7, nusModId7);
        puModuleSessionBean.associatePUModuleNUSModule(puModId8, nusModId8);
        puModuleSessionBean.associatePUModuleNUSModule(puModId9, nusModId9);
        puModuleSessionBean.associatePUModuleNUSModule(puModId10, nusModId10);
        puModuleSessionBean.associatePUModuleNUSModule(puModId11, nusModId11);
        puModuleSessionBean.associatePUModuleNUSModule(puModId12, nusModId12);
        puModuleSessionBean.associatePUModuleNUSModule(puModId13, nusModId13);
        puModuleSessionBean.associatePUModuleNUSModule(puModId14, nusModId14);
        puModuleSessionBean.associatePUModuleNUSModule(puModId15, nusModId15);
        puModuleSessionBean.associatePUModuleNUSModule(puModId16, nusModId16);
        puModuleSessionBean.associatePUModuleNUSModule(puModId17, nusModId17);
        puModuleSessionBean.associatePUModuleNUSModule(puModId18, nusModId18);
        puModuleSessionBean.associatePUModuleNUSModule(puModId19, nusModId19);
        puModuleSessionBean.associatePUModuleNUSModule(puModId20, nusModId20);
        puModuleSessionBean.associatePUModuleNUSModule(puModId21, nusModId21);
        puModuleSessionBean.associatePUModuleNUSModule(puModId22, nusModId22);
        puModuleSessionBean.associatePUModuleNUSModule(puModId23, nusModId23);
        puModuleSessionBean.associatePUModuleNUSModule(puModId24, nusModId24);
        puModuleSessionBean.associatePUModuleNUSModule(puModId25, nusModId25);
        puModuleSessionBean.associatePUModuleNUSModule(puModId26, nusModId26);
        puModuleSessionBean.associatePUModuleNUSModule(puModId27, nusModId27);
        puModuleSessionBean.associatePUModuleNUSModule(puModId28, nusModId28);
        puModuleSessionBean.associatePUModuleNUSModule(puModId29, nusModId29);
        puModuleSessionBean.associatePUModuleNUSModule(puModId30, nusModId30);
        puModuleSessionBean.associatePUModuleNUSModule(puModId31, nusModId31);
        puModuleSessionBean.associatePUModuleNUSModule(puModId32, nusModId32);
        puModuleSessionBean.associatePUModuleNUSModule(puModId33, nusModId33);
        puModuleSessionBean.associatePUModuleNUSModule(puModId34, nusModId34);
        puModuleSessionBean.associatePUModuleNUSModule(puModId35, nusModId35);
        puModuleSessionBean.associatePUModuleNUSModule(puModId36, nusModId36);
        puModuleSessionBean.associatePUModuleNUSModule(puModId37, nusModId37);
        puModuleSessionBean.associatePUModuleNUSModule(puModId38, nusModId38);
        puModuleSessionBean.associatePUModuleNUSModule(puModId39, nusModId39);
        puModuleSessionBean.associatePUModuleNUSModule(puModId40, nusModId40);
        puModuleSessionBean.associatePUModuleNUSModule(puModId41, nusModId41);
        puModuleSessionBean.associatePUModuleNUSModule(puModId42, nusModId42);
        puModuleSessionBean.associatePUModuleNUSModule(puModId43, nusModId43);
        puModuleSessionBean.associatePUModuleNUSModule(puModId44, nusModId44);
        puModuleSessionBean.associatePUModuleNUSModule(puModId45, nusModId45);
        puModuleSessionBean.associatePUModuleNUSModule(puModId46, nusModId46);
        puModuleSessionBean.associatePUModuleNUSModule(puModId47, nusModId47);
        puModuleSessionBean.associatePUModuleNUSModule(puModId48, nusModId48);
        puModuleSessionBean.associatePUModuleNUSModule(puModId49, nusModId49);
        puModuleSessionBean.associatePUModuleNUSModule(puModId50, nusModId50);
        puModuleSessionBean.associatePUModuleNUSModule(puModId51, nusModId51);
        puModuleSessionBean.associatePUModuleNUSModule(puModId52, nusModId52);
        puModuleSessionBean.associatePUModuleNUSModule(puModId53, nusModId53);
        puModuleSessionBean.associatePUModuleNUSModule(puModId54, nusModId54);
        puModuleSessionBean.associatePUModuleNUSModule(puModId55, nusModId55);
        puModuleSessionBean.associatePUModuleNUSModule(puModId56, nusModId56);
        puModuleSessionBean.associatePUModuleNUSModule(puModId57, nusModId57);
        puModuleSessionBean.associatePUModuleNUSModule(puModId58, nusModId58);
        puModuleSessionBean.associatePUModuleNUSModule(puModId59, nusModId59);
        puModuleSessionBean.associatePUModuleNUSModule(puModId60, nusModId60);
        puModuleSessionBean.associatePUModuleNUSModule(puModId61, nusModId61);
        puModuleSessionBean.associatePUModuleNUSModule(puModId62, nusModId62);
        puModuleSessionBean.associatePUModuleNUSModule(puModId63, nusModId63);
        puModuleSessionBean.associatePUModuleNUSModule(puModId64, nusModId64);
        puModuleSessionBean.associatePUModuleNUSModule(puModId65, nusModId65);
        puModuleSessionBean.associatePUModuleNUSModule(puModId66, nusModId66);
        puModuleSessionBean.associatePUModuleNUSModule(puModId67, nusModId67);
        puModuleSessionBean.associatePUModuleNUSModule(puModId68, nusModId68);
        puModuleSessionBean.associatePUModuleNUSModule(puModId69, nusModId69);
        puModuleSessionBean.associatePUModuleNUSModule(puModId70, nusModId70);
        puModuleSessionBean.associatePUModuleNUSModule(puModId71, nusModId71);
        puModuleSessionBean.associatePUModuleNUSModule(puModId72, nusModId72);
        puModuleSessionBean.associatePUModuleNUSModule(puModId73, nusModId73);
        puModuleSessionBean.associatePUModuleNUSModule(puModId74, nusModId74);
        puModuleSessionBean.associatePUModuleNUSModule(puModId75, nusModId75);
        puModuleSessionBean.associatePUModuleNUSModule(puModId76, nusModId76);
        puModuleSessionBean.associatePUModuleNUSModule(puModId77, nusModId77);
        puModuleSessionBean.associatePUModuleNUSModule(puModId78, nusModId78);
        puModuleSessionBean.associatePUModuleNUSModule(puModId79, nusModId79);
        puModuleSessionBean.associatePUModuleNUSModule(puModId80, nusModId80);
        puModuleSessionBean.associatePUModuleNUSModule(puModId81, nusModId81);
        puModuleSessionBean.associatePUModuleNUSModule(puModId82, nusModId82);
        puModuleSessionBean.associatePUModuleNUSModule(puModId83, nusModId83);
        puModuleSessionBean.associatePUModuleNUSModule(puModId84, nusModId84);
        puModuleSessionBean.associatePUModuleNUSModule(puModId85, nusModId85);
        puModuleSessionBean.associatePUModuleNUSModule(puModId86, nusModId86);
        puModuleSessionBean.associatePUModuleNUSModule(puModId87, nusModId87);
        puModuleSessionBean.associatePUModuleNUSModule(puModId88, nusModId88);

        //Associate Ben Leong to take these 3 Modules
        puModuleSessionBean.associatePUModuleStudent(puModId1, studentId1);
        puModuleSessionBean.associatePUModuleStudent(puModId4, studentId1);
        puModuleSessionBean.associatePUModuleStudent(puModId7, studentId1);

        //Associate Ben Leong with these reviews
        PUModuleReview pumodulereview1 = new PUModuleReview(
                "This is a dummy review",
                new Long(3),
                new Integer(3),
                new Integer(0)
        );
        PUModuleReview pumodulereview2 = new PUModuleReview(
                "Test2",
                new Long(3),
                new Integer(1),
                new Integer(0)
        );

        pumodulereview1.setIsInappropriate(true);

        pumodulereview1.setStudent(student1);
        pumodulereview2.setStudent(student1);
        pumodulereview1.setModule(module1);
        pumodulereview2.setModule(module4);

        pUModuleReviewSessionBean.createPUModuleReview(pumodulereview1);
        pUModuleReviewSessionBean.createPUModuleReview(pumodulereview2);

        List<Long> listOfPUModules1 = new ArrayList<>();
        //        listOfPUModules1.add(mId1);
        //        listOfPUModules1.add(mId2);
        //
        //        Long pId1 = pUSessionBean.createNewPu(pu1, cId1, cId1);
        //        Long pId2 = pUSessionBean.createNewPu(pu2, cId2, cId2);
        //        Long pId3 = pUSessionBean.createNewPu(pu3, cId3, cId3);
        //        Long pId4 = pUSessionBean.createNewPu(pu4, cId4, cId4);
        //        Long pId5 = pUSessionBean.createNewPu(pu5, cId5, cId5);
        //        Long pId6 = pUSessionBean.createNewPu(pu5, listOfPUModules1);

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

        PUReview puReview11 = new PUReview(new Long(3), pId3);
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

        puReview1.setIsInappropriate(true);
        puReview2.setIsInappropriate(true);
        puReview3.setIsInappropriate(true);
        puReview4.setIsInappropriate(true);
        puReview5.setIsInappropriate(true);

        ForumTopic forumTopic1 = new ForumTopic("WTF Topic");
        ForumTopic forumTopic2 = new ForumTopic("CCB Topic");
        ForumTopic forumTopic3 = new ForumTopic("CHAO Topic");
        ForumTopic forumTopic4 = new ForumTopic("Fourth Topic");
        ForumTopic forumTopic5 = new ForumTopic("Fifth Topic");

        forumTopic1.setIsInappropriate(true);
        forumTopic2.setIsInappropriate(true);
        forumTopic3.setIsInappropriate(true);

        forumTopicSessionBeanLocal.createNewForumTopic(
                forumTopic1,
                studentId1,
                pId1
        );
        forumTopicSessionBeanLocal.createNewForumTopic(
                forumTopic2,
                studentId2,
                pId2
        );
        forumTopicSessionBeanLocal.createNewForumTopic(
                forumTopic3,
                studentId3,
                pId3
        );
        forumTopicSessionBeanLocal.createNewForumTopic(
                forumTopic4,
                studentId1,
                pId4
        );
        forumTopicSessionBeanLocal.createNewForumTopic(
                forumTopic5,
                studentId2,
                pId5
        );

        ForumPost forumPost1 = new ForumPost("This is stupid?", "Stupid PU!!");
        ForumPost forumPost2 = new ForumPost("HK nice food?", "What a joke!!");
        ForumPost forumPost3 = new ForumPost(
                "Places to stay?",
                "I want to stay in Singapore"
        );

        forumPost1.setIsInappropriate(true);
        forumPost2.setIsInappropriate(true);

        forumPost1.setNoOfLikes(0);
        forumPost1.setNoOfDislikes(0);

        forumPost2.setNoOfLikes(0);
        forumPost2.setNoOfDislikes(0);

        forumPost1.getDislikedStudents().add(student1.getStudentId());
        forumPost1.getDislikedStudents().add(student2.getStudentId());

        forumPostSessionBeanLocal.createNewForumPost(
                forumPost1,
                forumTopic2.getTopicId(),
                student1.getStudentId()
        );
        forumPostSessionBeanLocal.createNewForumPost(
                forumPost2,
                forumTopic2.getTopicId(),
                student3.getStudentId()
        );
        forumPostSessionBeanLocal.createNewForumPost(
                forumPost3,
                forumTopic1.getTopicId(),
                student2.getStudentId()
        );

        ForumComment forumComment1 = new ForumComment(
                "Wow this is such a fking shit!",
                false
        );
        ForumComment forumComment2 = new ForumComment("fk you uds", false);
        ForumComment forumComment3 = new ForumComment("Marina Bay Sands!", false);

        forumComment1.setIsInappropriate(true);
        forumComment2.setIsInappropriate(true);

        forumComment1.setNoOfLikes(0);
        forumComment1.setNoOfDislikes(0);

        forumComment2.setNoOfLikes(0);
        forumComment2.setNoOfDislikes(0);

        forumCommentSessionBeanLocal.createNewForumComment(
                forumComment1,
                forumPost1.getPostId(),
                student3.getStudentId()
        );
        forumCommentSessionBeanLocal.createNewForumComment(
                forumComment2,
                forumPost2.getPostId(),
                student2.getStudentId()
        );
        forumCommentSessionBeanLocal.createNewForumComment(
                forumComment3,
                forumPost3.getPostId(),
                student1.getStudentId()
        );
        //        Enquiry enquiry1 = new Enquiry("Hello", "Help");
        //        Enquiry enquiry2 = new Enquiry("Bye", "World");
        //        Enquiry enquiry3 = new Enquiry("Interesting", "Story");
        //
        //        studentEnquirySessionBeanLocal.createEnquiry(enquiry1, 1L);
        //        studentEnquirySessionBeanLocal.createEnquiry(enquiry2, 1L);
        //        studentEnquirySessionBeanLocal.createEnquiry(enquiry3, 2L);
    }

    private void createData() throws NoResultException {
        NUSModule nusModule1 = new NUSModule(
                "Enterprise Systems Interface Design and Development",
                "IS3106",
                "This module aims to train students to be conversant in front-end development for Enterprise Systems. It complements IS2103 which focuses on backend development aspects for Enterprise Systems. Topics covered include web development scripting languages, web templating design and component design, integrating with backend application, and basic mobile application development."
        );

        NUSModule nusModule2 = new NUSModule(
                "Computational Thinking",
                "CS1010",
                "The module provides a gentle introduction to programming using Python programming language. It aims to provide students with an understanding of the role computation can play in solving problems. It also aims to help students, regardless of their major, to feel justifiably confident of their ability to write small programs that allow them to accomplish useful goals."
        );

        NUSModule nusModule3 = new NUSModule(
                "Data Structures and Algorithms",
                "CS2040",
                "This module is about the design, analysis, and implementation of data structures and algorithms. It covers classical data structures (linked lists, stacks, queues, trees, and graphs), and algorithms (sorting, searching, and graph traversal). It also includes an introduction to algorithmic complexity and intractability."
        );

        NUSModule nusModule4 = new NUSModule(
                "Programming Methodology",
                "CS1101S",
                "This module aims to teach students how to program systematically, and how to develop good programming habits. It uses a variant of the Racket programming language to solidify the concepts learned in class."
        );

        NUSModule nusModule5 = new NUSModule(
                "Programming Methodology II",
                "CS2030",
                "This module aims to teach students how to design and develop larger programs, and how to write programs that are more reliable, efficient, and maintainable. It covers topics such as abstraction, interfaces, data representation, and program correctness. It also includes an introduction to concurrent programming."
        );

        NUSModule nusModule6 = new NUSModule(
                "Introduction to Computer Networks",
                "CS2105",
                "This module provides an introduction to computer networks, covering the layered architecture of computer networks and the services and protocols that are provided at each layer."
        );

        NUSModule nusModule7 = new NUSModule(
                "Database Systems",
                "CS2102",
                "This module covers the principles and techniques of database systems. It includes topics such as data models, relational algebra, SQL, and transaction processing. It also covers topics such as query optimization, concurrency control, and recovery."
        );

        NUSModule nusModule8 = new NUSModule(
                "Operating Systems",
                "CS2106",
                "This module covers the principles and concepts of operating systems, including process management, memory management, file systems, and input/output."
        );

        NUSModule nusModule9 = new NUSModule(
                "Computer Security",
                "CS2107",
                "This module covers the principles and techniques of computer security. It includes topics such as access control, authentication, confidentiality, integrity, and availability. It also covers topics such as network security, web security, and malware."
        );

        NUSModule nusModule10 = new NUSModule(
                "Software Engineering",
                "CS2103T",
                "This module covers the principles and practices of software engineering, including requirements elicitation, software design, implementation, testing, and maintenance. It also covers topics such as project management, software process, and software quality."
        );
        //        PUReview pUReview1 = new PUReview(1L, "Meh", 2, 100, false);
        //        PUReview pUReview2 = new PUReview(2L, "Meh", 3, 10, false);
        //        PUReview pUReview3 = new PUReview(3L, "Meh", 4, 20, true);
        //
        //        pUReviewSessionBean.createPUReview(pUReview1, 1L);
        //        pUReviewSessionBean.createPUReview(pUReview2, 1L);
        //        pUReviewSessionBean.createPUReview(pUReview3, 1L);
    }
}
