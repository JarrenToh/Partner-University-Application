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
import entity.FAQ;
import entity.Faculty;
import entity.ForumComment;
import entity.ForumPost;
import entity.ForumTopic;
import entity.NUSModule;
import entity.NUSchangeAdmin;
import entity.PUModuleReview;
import entity.Student;
import entity.PU;
import entity.PUModule;
import entity.PUReview;
import entity.Region;
import error.NoResultException;
import java.util.ArrayList;
import java.util.List;
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
        Student student4 = new Student("Sarah", "Tan", "90002041", "sarahtan@comp.nus.edu.sg", "password");
        Student student5 = new Student("Jason", "Lee", "90002042", "jasonlee@comp.nus.edu.sg", "password");
        Student student6 = new Student("Jasmine", "Lim", "90002043", "jasminelim@comp.nus.edu.sg", "password");
        Student student7 = new Student("Alex", "Koh", "90002044", "alexkoh@comp.nus.edu.sg", "password");
        Student student8 = new Student("Grace", "Teo", "90002045", "graceteo@comp.nus.edu.sg", "password");
        Student student9 = new Student("David", "Ng", "90002046", "davidng@comp.nus.edu.sg", "password");
        Student student10 = new Student("Jessica", "Wong", "90002047", "jessicawong@comp.nus.edu.sg", "password");
        Student student11 = new Student("Alan", "Lim", "90002048", "alanlim@comp.nus.edu.sg", "password");
        Student student12 = new Student("Lily", "Tan", "90002049", "lilytan@comp.nus.edu.sg", "password");
        Student student13 = new Student("Kevin", "Chen", "90002050", "kevinchen@comp.nus.edu.sg", "password");
        Student student14 = new Student("Amy", "Tan", "90002051", "amytan@comp.nus.edu.sg", "password");
        Student student15 = new Student("Brian", "Lim", "90002052", "brianlim@comp.nus.edu.sg", "password");
        Student student16 = new Student("Emily", "Ng", "90002053", "emilyng@comp.nus.edu.sg", "password");
        Student student17 = new Student("Victor", "Koh", "90002054", "victorkoh@comp.nus.edu.sg", "password");
        Student student18 = new Student("Natalie", "Tan", "90002055", "natalietan@comp.nus.edu.sg", "password");
        Student student19 = new Student("Samuel", "Tan", "90002056", "samueltan@comp.nus.edu.sg", "password");
        Student student20 = new Student("Cindy", "Lee", "90002057", "cindylee@comp.nus.edu.sg", "password");
        Student student21 = new Student("John", "Doe", "90002041", "johndoe@comp.nus.edu.sg", "password");
        Student student22 = new Student("Jane", "Doe", "90002042", "janedoe@comp.nus.edu.sg", "password");
        Student student23 = new Student("David", "Smith", "90002043", "davidsmith@comp.nus.edu.sg", "password");
        Student student24 = new Student("Sarah", "Johnson", "90002044", "sarahjohnson@comp.nus.edu.sg", "password");
        Student student25 = new Student("Michael", "Lee", "90002045", "michaellee@comp.nus.edu.sg", "password");
        Student student26 = new Student("Emily", "Wong", "90002046", "emilywong@comp.nus.edu.sg", "password");
        Student student27 = new Student("Daniel", "Tan", "90002047", "danieltan@comp.nus.edu.sg", "password");
        Student student28 = new Student("Rachel", "Lim", "90002048", "rachellim@comp.nus.edu.sg", "password");
        Student student29 = new Student("William", "Ng", "90002049", "williamng@comp.nus.edu.sg", "password");
        Student student30 = new Student("Michelle", "Tan", "90002050", "michelletan@comp.nus.edu.sg", "password");
        Student student31 = new Student("Andrew", "Chen", "90002051", "andrewchen@comp.nus.edu.sg", "password");
        Student student32 = new Student("Melissa", "Koh", "90002052", "melissakoh@comp.nus.edu.sg", "password");
        Student student33 = new Student("Jason", "Ong", "90002053", "jasonong@comp.nus.edu.sg", "password");
        Student student34 = new Student("Samantha", "Teo", "90002054", "samanthateo@comp.nus.edu.sg", "password");
        Student student35 = new Student("Nicholas", "Tan", "90002055", "nicholastan@comp.nus.edu.sg", "password");
        Student student36 = new Student("Amanda", "Chua", "90002056", "amandachua@comp.nus.edu.sg", "password");
        Student student37 = new Student("Kevin", "Ng", "90002057", "kevinng@comp.nus.edu.sg", "password");
        Student student38 = new Student("Grace", "Loh", "90002058", "graceloh@comp.nus.edu.sg", "password");
        Student student39 = new Student("Justin", "Lim", "90002059", "justinlim@comp.nus.edu.sg", "password");
        Student student40 = new Student("Hannah", "Tan", "90002060", "hannahtan@comp.nus.edu.sg", "password");

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

        PUModuleReview pumodulereview1 = new PUModuleReview("Test1", new Long(2), new Integer(1), new Integer(1));
        PUModuleReview pumodulereview2 = new PUModuleReview("Test2", new Long(2), new Integer(1), new Integer(1));

        pumodulereview1.setIsInappropriate(true);

        pumodulereview1.setStudent(student1);
        pumodulereview1.setNoOfDislikes(0);
        pumodulereview1.setNoOfDislikes(0);

        pUModuleReviewSessionBean.createPUModuleReview(pumodulereview1);
        pUModuleReviewSessionBean.createPUModuleReview(pumodulereview2);

        Faculty faculty1 = new Faculty("Faculty of Arts and Social Sciences (FASS)");
        Faculty faculty2 = new Faculty("School of Business (NUS Business School)");
        Faculty faculty3 = new Faculty("School of Computing (SoC)");
        Faculty faculty4 = new Faculty("Faculty of Dentistry (FoD)");
        Faculty faculty5 = new Faculty("School of Design and Environment (SDE)");
        Faculty faculty6 = new Faculty("Faculty of Engineering (FoE)");
        Faculty faculty7 = new Faculty("Faculty of Law (FoL)");
        Faculty faculty8 = new Faculty("Yong Loo Lin School of Medicine (NUS Medicine)");
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

        NUSModule nusModule1 = new NUSModule("Enterprise Systems Interface Design and Development", "IS3106", "This module aims to train students to be conversant in front-end development for Enterprise Systems. It complements IS2103 which focuses on backend development aspects for Enterprise Systems. Topics covered include web development scripting languages, web templating design and component design, integrating with backend application, and basic mobile application development.");
        NUSModule nusModule2 = new NUSModule("Computational Thinking", "CS1010", "The module provides a gentle introduction to programming using Python programming language. It aims to provide students with an understanding of the role computation can play in solving problems. It also aims to help students, regardless of their major, to feel justifiably confident of their ability to write small programs that allow them to accomplish useful goals.");
        NUSModule nusModule3 = new NUSModule("Data Structures and Algorithms", "CS2040", "This module is about the design, analysis, and implementation of data structures and algorithms. It covers classical data structures (linked lists, stacks, queues, trees, and graphs), and algorithms (sorting, searching, and graph traversal). It also includes an introduction to algorithmic complexity and intractability.");
        NUSModule nusModule4 = new NUSModule("Programming Methodology", "CS1101S", "This module aims to teach students how to program systematically, and how to develop good programming habits. It uses a variant of the Racket programming language to solidify the concepts learned in class.");
        NUSModule nusModule5 = new NUSModule("Programming Methodology II", "CS2030", "This module aims to teach students how to design and develop larger programs, and how to write programs that are more reliable, efficient, and maintainable. It covers topics such as abstraction, interfaces, data representation, and program correctness. It also includes an introduction to concurrent programming.");
        NUSModule nusModule6 = new NUSModule("Introduction to Computer Networks", "CS2105", "This module provides an introduction to computer networks, covering the layered architecture of computer networks and the services and protocols that are provided at each layer.");
        NUSModule nusModule7 = new NUSModule("Database Systems", "CS2102", "This module covers the principles and techniques of database systems. It includes topics such as data models, relational algebra, SQL, and transaction processing. It also covers topics such as query optimization, concurrency control, and recovery.");
        NUSModule nusModule8 = new NUSModule("Operating Systems", "CS2106", "This module covers the principles and concepts of operating systems, including process management, memory management, file systems, and input/output.");
        NUSModule nusModule9 = new NUSModule("Computer Security", "CS2107", "This module covers the principles and techniques of computer security. It includes topics such as access control, authentication, confidentiality, integrity, and availability. It also covers topics such as network security, web security, and malware.");
        NUSModule nusModule10 = new NUSModule("Software Engineering", "CS2103T", "This module covers the principles and practices of software engineering, including requirements elicitation, software design, implementation, testing, and maintenance. It also covers topics such as project management, software process, and software quality.");

        Long nusModId1 = nUSModuleSessionBean.createNUSModule(nusModule1, facultyId3);
        Long nusModId2 = nUSModuleSessionBean.createNUSModule(nusModule2, facultyId3);
        Long nusModId3 = nUSModuleSessionBean.createNUSModule(nusModule3, facultyId3);
        Long nusModId4 = nUSModuleSessionBean.createNUSModule(nusModule4, facultyId3);
        Long nusModId5 = nUSModuleSessionBean.createNUSModule(nusModule5, facultyId3);
        Long nusModId6 = nUSModuleSessionBean.createNUSModule(nusModule6, facultyId3);
        Long nusModId7 = nUSModuleSessionBean.createNUSModule(nusModule7, facultyId3);
        Long nusModId8 = nUSModuleSessionBean.createNUSModule(nusModule8, facultyId3);
        Long nusModId9 = nUSModuleSessionBean.createNUSModule(nusModule9, facultyId3);
        Long nusModId10 = nUSModuleSessionBean.createNUSModule(nusModule10, facultyId3);

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

        NUSModule nusModule11 = new NUSModule("Introduction to Political Science", "PS1101E", "This module introduces students to the study of politics and political science. It covers topics such as political ideologies, political systems and institutions, and political processes and behavior.");
        NUSModule nusModule12 = new NUSModule("Understanding Southeast Asia", "SE1101E", "This module provides an overview of the social, cultural, economic, and political dimensions of Southeast Asia. It covers topics such as the history and diversity of the region, its relations with the world, and contemporary issues and challenges.");
        NUSModule nusModule13 = new NUSModule("Introduction to Sociology", "SC1101E", "This module introduces students to the study of sociology and sociological theories. It covers topics such as social structures, socialization and identity, social inequality and stratification, and social change.");
        NUSModule nusModule14 = new NUSModule("Introduction to Psychology", "PSY1101E", "This module provides an overview of the field of psychology and the scientific study of behavior and mental processes. It covers topics such as biological bases of behavior, perception, learning and memory, and social cognition and attitudes.");
        NUSModule nusModule15 = new NUSModule("World History: An Introduction", "HY1101E", "This module offers an introduction to world history from pre-modern times to the present. It covers key events and processes that have shaped human societies and cultures, and highlights their global and comparative dimensions.");
        NUSModule nusModule16 = new NUSModule("Introduction to Literature in English", "EN1101E", "This module introduces students to the study of literature in English, and aims to develop critical reading and analytical skills. It covers different literary genres and forms, and examines the cultural and historical contexts of literary works.");
        NUSModule nusModule17 = new NUSModule("Introduction to Theatre Studies", "TS1101E", "This module introduces students to the study of theatre and performance, and aims to develop critical and creative thinking skills. It covers different theatrical forms and practices, and examines their social, cultural, and political contexts.");
        NUSModule nusModule18 = new NUSModule("Introduction to Philosophy", "PL1101E", "This module introduces students to the study of philosophy and its central questions and debates. It covers topics such as the nature of reality, knowledge and skepticism, ethics and morality, and the meaning of life.");
        NUSModule nusModule19 = new NUSModule("Introduction to Southeast Asian Studies", "SSA1201E", "This module provides an interdisciplinary introduction to the study of Southeast Asia, and aims to develop critical thinking and research skills. It covers topics such as the region's history, society and culture, politics and governance, and economic development.");
        NUSModule nusModule20 = new NUSModule("Introduction to Communications and New Media", "NM1101E", "This module introduces students to the study of communications and new media, and examines their social, cultural, and political dimensions. It covers topics such as media technologies, digital cultures, and communication theories and practices.");

        Long nusModId11 = nUSModuleSessionBean.createNUSModule(nusModule11, facultyId1);
        Long nusModId12 = nUSModuleSessionBean.createNUSModule(nusModule12, facultyId1);
        Long nusModId13 = nUSModuleSessionBean.createNUSModule(nusModule13, facultyId1);
        Long nusModId14 = nUSModuleSessionBean.createNUSModule(nusModule14, facultyId1);
        Long nusModId15 = nUSModuleSessionBean.createNUSModule(nusModule15, facultyId1);
        Long nusModId16 = nUSModuleSessionBean.createNUSModule(nusModule16, facultyId1);
        Long nusModId17 = nUSModuleSessionBean.createNUSModule(nusModule17, facultyId1);
        Long nusModId18 = nUSModuleSessionBean.createNUSModule(nusModule18, facultyId1);
        Long nusModId19 = nUSModuleSessionBean.createNUSModule(nusModule19, facultyId1);
        Long nusModId20 = nUSModuleSessionBean.createNUSModule(nusModule20, facultyId1);

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

        NUSModule nusModule21 = new NUSModule("Introduction to Accounting", "ACC1002", "This module provides an introduction to financial accounting concepts and principles. It covers topics such as the accounting equation, financial statements, accounting cycles, and accounting systems.");
        NUSModule nusModule22 = new NUSModule("Introduction to Marketing", "MKT1003", "This module introduces students to the fundamental concepts and principles of marketing. It covers topics such as market segmentation, product development, pricing strategies, promotional mix, and consumer behavior.");
        NUSModule nusModule23 = new NUSModule("Introduction to Finance", "FIN1002", "This module provides an introduction to financial management concepts and principles. It covers topics such as time value of money, risk and return, capital budgeting, financial markets, and financial instruments.");
        NUSModule nusModule24 = new NUSModule("Business Law", "BSP1004", "This module introduces students to the legal aspects of business operations. It covers topics such as contracts, torts, intellectual property, business organizations, and international business law.");
        NUSModule nusModule25 = new NUSModule("Operations Management", "OMG1005", "This module introduces students to the principles and practices of operations management. It covers topics such as process design, capacity planning, inventory management, quality control, and supply chain management.");
        NUSModule nusModule26 = new NUSModule("Business Analytics", "DSA2102", "This module provides an introduction to data analytics and its applications in business decision-making. It covers topics such as data visualization, statistical inference, regression analysis, and predictive modeling.");
        NUSModule nusModule27 = new NUSModule("Strategic Management", "BSP3001", "This module provides an overview of strategic management concepts and frameworks. It covers topics such as strategic analysis, strategy formulation, implementation, and evaluation, and examines the role of organizational culture and leadership in strategic decision-making.");
        NUSModule nusModule28 = new NUSModule("Entrepreneurship", "BSP3007", "This module introduces students to the principles and practices of entrepreneurship. It covers topics such as idea generation, business planning, funding and financing, marketing and sales, and managing growth and expansion.");
        NUSModule nusModule29 = new NUSModule("International Business", "BSP3513", "This module provides an overview of international business operations and strategies. It covers topics such as international trade theories, foreign direct investment, global business environment, cross-cultural management, and international marketing.");
        NUSModule nusModule30 = new NUSModule("Corporate Social Responsibility", "BSP3514", "This module examines the concept of corporate social responsibility and its implications for business operations and strategies. It covers topics such as ethical decision-making, stakeholder engagement, sustainability reporting, and social impact assessment.");

        Long nusModId21 = nUSModuleSessionBean.createNUSModule(nusModule21, facultyId2);
        Long nusModId22 = nUSModuleSessionBean.createNUSModule(nusModule22, facultyId2);
        Long nusModId23 = nUSModuleSessionBean.createNUSModule(nusModule23, facultyId2);
        Long nusModId24 = nUSModuleSessionBean.createNUSModule(nusModule24, facultyId2);
        Long nusModId25 = nUSModuleSessionBean.createNUSModule(nusModule25, facultyId2);
        Long nusModId26 = nUSModuleSessionBean.createNUSModule(nusModule26, facultyId2);
        Long nusModId27 = nUSModuleSessionBean.createNUSModule(nusModule27, facultyId2);
        Long nusModId28 = nUSModuleSessionBean.createNUSModule(nusModule28, facultyId2);
        Long nusModId29 = nUSModuleSessionBean.createNUSModule(nusModule29, facultyId2);
        Long nusModId30 = nUSModuleSessionBean.createNUSModule(nusModule30, facultyId2);

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

        NUSModule nusModule31 = new NUSModule("Oral Biology", "DEN1101", "This module introduces students to the biological principles and processes related to the oral cavity. It covers topics such as oral anatomy, histology, embryology, and microbiology.");
        NUSModule nusModule32 = new NUSModule("Dental Materials", "DEN1201", "This module provides an overview of the materials commonly used in dentistry, and their properties, selection criteria, and clinical applications. It covers topics such as restorative materials, impression materials, and dental ceramics.");
        NUSModule nusModule33 = new NUSModule("Oral Health Promotion", "DEN1301", "This module examines the principles and strategies for promoting oral health and preventing oral diseases at the individual and community levels. It covers topics such as health education, behavior change, and social determinants of oral health.");
        NUSModule nusModule34 = new NUSModule("Dental Anatomy and Occlusion", "DEN2101", "This module provides an in-depth study of dental anatomy and occlusion, and their clinical implications for restorative and prosthodontic treatment planning. It covers topics such as tooth morphology, occlusal relationships, and functional anatomy.");
        NUSModule nusModule35 = new NUSModule("Clinical Periodontology", "DEN2201", "This module focuses on the diagnosis, treatment, and prevention of periodontal diseases, and their impact on oral and systemic health. It covers topics such as periodontal anatomy and physiology, periodontal assessment, and nonsurgical and surgical therapy.");
        NUSModule nusModule36 = new NUSModule("Oral and Maxillofacial Surgery", "DEN2301", "This module provides an introduction to the principles and practices of oral and maxillofacial surgery, and their clinical applications for the management of oral and facial conditions. It covers topics such as surgical anatomy, anesthesia, and surgical techniques.");
        NUSModule nusModule37 = new NUSModule("Clinical Endodontics", "DEN3101", "This module focuses on the diagnosis, treatment, and prevention of pulpal and periapical diseases, and their management using endodontic techniques. It covers topics such as pulp biology, root canal anatomy, and endodontic instrumentation and obturation.");
        NUSModule nusModule38 = new NUSModule("Orthodontics", "DEN3201", "This module introduces students to the principles and practices of orthodontics, and their clinical applications for the correction of malocclusions and dentofacial deformities. It covers topics such as tooth movement, growth and development, and orthodontic appliances.");
        NUSModule nusModule39 = new NUSModule("Prosthodontics", "DEN3301", "This module provides an overview of the principles and practices of prosthodontics, and their clinical applications for the rehabilitation of oral function and aesthetics. It covers topics such as dental materials, fixed and removable prosthodontics, and implant dentistry.");

        Long nusModId31 = nUSModuleSessionBean.createNUSModule(nusModule31, facultyId4);
        Long nusModId32 = nUSModuleSessionBean.createNUSModule(nusModule32, facultyId4);
        Long nusModId33 = nUSModuleSessionBean.createNUSModule(nusModule33, facultyId4);
        Long nusModId34 = nUSModuleSessionBean.createNUSModule(nusModule34, facultyId4);
        Long nusModId35 = nUSModuleSessionBean.createNUSModule(nusModule35, facultyId4);
        Long nusModId36 = nUSModuleSessionBean.createNUSModule(nusModule36, facultyId4);
        Long nusModId37 = nUSModuleSessionBean.createNUSModule(nusModule37, facultyId4);
        Long nusModId38 = nUSModuleSessionBean.createNUSModule(nusModule38, facultyId4);
        Long nusModId39 = nUSModuleSessionBean.createNUSModule(nusModule39, facultyId4);

        nUSModuleSessionBean.createNUSModule(nusModule31, facultyId4);
        nUSModuleSessionBean.createNUSModule(nusModule32, facultyId4);
        nUSModuleSessionBean.createNUSModule(nusModule33, facultyId4);
        nUSModuleSessionBean.createNUSModule(nusModule34, facultyId4);
        nUSModuleSessionBean.createNUSModule(nusModule35, facultyId4);
        nUSModuleSessionBean.createNUSModule(nusModule36, facultyId4);
        nUSModuleSessionBean.createNUSModule(nusModule37, facultyId4);
        nUSModuleSessionBean.createNUSModule(nusModule38, facultyId4);
        nUSModuleSessionBean.createNUSModule(nusModule39, facultyId4);

        NUSModule nusModule40 = new NUSModule("Introduction to Environmental Systems and Sustainable Development", "ESD1101", "This module introduces students to the concepts and principles of environmental systems and sustainable development. It covers topics such as ecosystems and biodiversity, climate change, energy and resources, and sustainable urban development.");
        NUSModule nusModule41 = new NUSModule("Building Construction", "BC1101", "This module introduces students to the principles and practices of building construction. It covers topics such as building materials and systems, construction processes and techniques, and building regulations and codes.");
        NUSModule nusModule42 = new NUSModule("Architectural Design", "AR1101", "This module introduces students to the fundamentals of architectural design. It covers topics such as design principles and processes, site analysis and planning, and architectural representation and communication.");
        NUSModule nusModule43 = new NUSModule("Landscape Architecture", "LA1101", "This module introduces students to the theory and practice of landscape architecture. It covers topics such as site analysis and planning, landscape design principles and processes, and sustainable landscape management.");
        NUSModule nusModule44 = new NUSModule("Urban Planning and Design", "UP1101", "This module introduces students to the theory and practice of urban planning and design. It covers topics such as urbanization and urban systems, planning principles and processes, and the design of urban spaces and infrastructure.");
        NUSModule nusModule45 = new NUSModule("Industrial Design", "ID1101", "This module introduces students to the theory and practice of industrial design. It covers topics such as design thinking and methodology, product design and development, and manufacturing processes and technologies.");
        NUSModule nusModule46 = new NUSModule("Interior Design", "ID1201", "This module introduces students to the theory and practice of interior design. It covers topics such as spatial design principles and processes, materials and finishes, and lighting and environmental systems.");
        NUSModule nusModule47 = new NUSModule("Design Communication", "DC1101", "This module introduces students to the principles and techniques of design communication. It covers topics such as visual communication, digital media and tools, and design presentation and critique.");
        NUSModule nusModule48 = new NUSModule("Construction and Project Management", "CPD1101", "This module introduces students to the principles and practices of construction and project management. It covers topics such as project planning and scheduling, cost estimation and control, and quality assurance and safety.");
        NUSModule nusModule49 = new NUSModule("Real Estate and Urban Economics", "RE1701", "This module introduces students to the theory and practice of real estate and urban economics. It covers topics such as real estate markets and valuation, land use and development, and urban economic policy and planning.");

        Long nusModId40 = nUSModuleSessionBean.createNUSModule(nusModule40, facultyId5);
        Long nusModId41 = nUSModuleSessionBean.createNUSModule(nusModule41, facultyId5);
        Long nusModId42 = nUSModuleSessionBean.createNUSModule(nusModule42, facultyId5);
        Long nusModId43 = nUSModuleSessionBean.createNUSModule(nusModule43, facultyId5);
        Long nusModId44 = nUSModuleSessionBean.createNUSModule(nusModule44, facultyId5);
        Long nusModId45 = nUSModuleSessionBean.createNUSModule(nusModule45, facultyId5);
        Long nusModId46 = nUSModuleSessionBean.createNUSModule(nusModule46, facultyId5);
        Long nusModId47 = nUSModuleSessionBean.createNUSModule(nusModule47, facultyId5);
        Long nusModId48 = nUSModuleSessionBean.createNUSModule(nusModule48, facultyId5);
        Long nusModId49 = nUSModuleSessionBean.createNUSModule(nusModule49, facultyId5);

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

        NUSModule nusModule50 = new NUSModule("Introduction to Engineering", "EG1108", "This module provides an overview of the various disciplines of engineering and their applications in the real world. It covers topics such as the engineering design process, basic engineering principles, and case studies of engineering projects.");
        NUSModule nusModule51 = new NUSModule("Introduction to Computer Science", "CS1010", "This module introduces students to the fundamental concepts of computer science, including programming, algorithms, and data structures. It also covers software engineering principles and basic computer architecture.");
        NUSModule nusModule52 = new NUSModule("Introduction to Electrical and Electronic Engineering", "EE1001X", "This module provides an introduction to the principles of electrical and electronic engineering. It covers topics such as electric circuits, electronics, and digital systems.");
        NUSModule nusModule53 = new NUSModule("Introduction to Materials Science and Engineering", "EG1413", "This module introduces the basic principles of materials science and engineering. It covers topics such as the structure and properties of materials, phase diagrams, and materials selection.");
        NUSModule nusModule54 = new NUSModule("Introduction to Biomedical Engineering", "BME101", "This module provides an overview of the interdisciplinary field of biomedical engineering. It covers topics such as medical imaging, biomechanics, and biomaterials.");
        NUSModule nusModule55 = new NUSModule("Introduction to Chemical Engineering", "EG1109", "This module introduces the fundamental principles of chemical engineering. It covers topics such as material and energy balances, thermodynamics, and transport phenomena.");
        NUSModule nusModule56 = new NUSModule("Introduction to Environmental Engineering", "EG1401", "This module provides an introduction to the principles of environmental engineering. It covers topics such as water and wastewater treatment, air pollution control, and solid waste management.");
        NUSModule nusModule57 = new NUSModule("Introduction to Mechanical Engineering", "ME1101E", "This module introduces the fundamental principles of mechanical engineering. It covers topics such as statics, dynamics, and mechanics of materials.");
        NUSModule nusModule58 = new NUSModule("Introduction to Aerospace Engineering", "ME2114", "This module provides an overview of the principles of aerospace engineering. It covers topics such as aerodynamics, aircraft structures, and propulsion systems.");
        NUSModule nusModule59 = new NUSModule("Introduction to Engineering Mathematics", "MA1505", "This module introduces the mathematical tools and concepts used in engineering. It covers topics such as calculus, linear algebra, and differential equations.");

        Long nusModId50 = nUSModuleSessionBean.createNUSModule(nusModule50, facultyId6);
        Long nusModId51 = nUSModuleSessionBean.createNUSModule(nusModule51, facultyId6);
        Long nusModId52 = nUSModuleSessionBean.createNUSModule(nusModule52, facultyId6);
        Long nusModId53 = nUSModuleSessionBean.createNUSModule(nusModule53, facultyId6);
        Long nusModId54 = nUSModuleSessionBean.createNUSModule(nusModule54, facultyId6);
        Long nusModId55 = nUSModuleSessionBean.createNUSModule(nusModule55, facultyId6);
        Long nusModId56 = nUSModuleSessionBean.createNUSModule(nusModule56, facultyId6);
        Long nusModId57 = nUSModuleSessionBean.createNUSModule(nusModule57, facultyId6);
        Long nusModId58 = nUSModuleSessionBean.createNUSModule(nusModule58, facultyId6);
        Long nusModId59 = nUSModuleSessionBean.createNUSModule(nusModule59, facultyId6);

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

        NUSModule nusModule60 = new NUSModule("Introduction to Law", "LAW1101", "This module introduces students to the study of law and legal systems. It covers topics such as the sources of law, legal reasoning, and the administration of justice.");
        NUSModule nusModule61 = new NUSModule("Criminal Law", "LAW2201", "This module provides an introduction to the principles and concepts of criminal law. It covers topics such as the elements of criminal offenses, defenses, and sentencing.");
        NUSModule nusModule62 = new NUSModule("Contract Law", "LAW2202", "This module provides an introduction to the principles and concepts of contract law. It covers topics such as offer and acceptance, consideration, and breach of contract.");
        NUSModule nusModule63 = new NUSModule("Tort Law", "LAW2203", "This module provides an introduction to the principles and concepts of tort law. It covers topics such as negligence, nuisance, and strict liability.");
        NUSModule nusModule64 = new NUSModule("Property Law", "LAW2204", "This module provides an introduction to the principles and concepts of property law. It covers topics such as estates and interests, trusts, and real property transactions.");
        NUSModule nusModule65 = new NUSModule("Constitutional Law", "LAW3201", "This module provides an introduction to the principles and concepts of constitutional law. It covers topics such as the sources and structure of the constitution, and the powers of the executive, legislature, and judiciary.");
        NUSModule nusModule66 = new NUSModule("Administrative Law", "LAW3202", "This module provides an introduction to the principles and concepts of administrative law. It covers topics such as judicial review, administrative tribunals, and the rule of law.");
        NUSModule nusModule67 = new NUSModule("International Law", "LAW3203", "This module provides an introduction to the principles and concepts of international law. It covers topics such as the sources and subjects of international law, the law of treaties, and the settlement of disputes.");
        NUSModule nusModule68 = new NUSModule("Commercial Law", "LAW3204", "This module provides an introduction to the principles and concepts of commercial law. It covers topics such as sales, agency, and the law of business organizations.");
        NUSModule nusModule69 = new NUSModule("Family Law", "LAW3205", "This module provides an introduction to the principles and concepts of family law. It covers topics such as marriage and divorce, child custody, and the legal rights and obligations of family members.");

        Long nusModId60 = nUSModuleSessionBean.createNUSModule(nusModule60, facultyId7);
        Long nusModId61 = nUSModuleSessionBean.createNUSModule(nusModule61, facultyId7);
        Long nusModId62 = nUSModuleSessionBean.createNUSModule(nusModule62, facultyId7);
        Long nusModId63 = nUSModuleSessionBean.createNUSModule(nusModule63, facultyId7);
        Long nusModId64 = nUSModuleSessionBean.createNUSModule(nusModule64, facultyId7);
        Long nusModId65 = nUSModuleSessionBean.createNUSModule(nusModule65, facultyId7);
        Long nusModId66 = nUSModuleSessionBean.createNUSModule(nusModule66, facultyId7);
        Long nusModId67 = nUSModuleSessionBean.createNUSModule(nusModule67, facultyId7);
        Long nusModId68 = nUSModuleSessionBean.createNUSModule(nusModule68, facultyId7);
        Long nusModId69 = nUSModuleSessionBean.createNUSModule(nusModule69, facultyId7);

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

        NUSModule nusModule70 = new NUSModule("Anatomy", "MDP1101", "This module provides an overview of the human body's structure, including its organs, tissues, and systems.");
        NUSModule nusModule71 = new NUSModule("Physiology", "MDP1102", "This module covers the functions and mechanisms of the human body's cells, organs, and systems.");
        NUSModule nusModule72 = new NUSModule("Pharmacology", "MDP2101", "This module introduces students to the principles and mechanisms of drug actions in the human body.");
        NUSModule nusModule73 = new NUSModule("Pathology", "MDP2102", "This module covers the study of the nature, causes, and development of diseases in the human body.");
        NUSModule nusModule74 = new NUSModule("Medical Ethics and Law", "MDP3101", "This module explores the ethical and legal issues related to medical practice, including patient rights, confidentiality, and end-of-life care.");
        NUSModule nusModule75 = new NUSModule("Public Health", "MDP3102", "This module covers the principles and practice of public health, including disease prevention, health promotion, and health policy.");
        NUSModule nusModule76 = new NUSModule("Human Immunology", "MDP3103", "This module provides an in-depth understanding of the human immune system and its role in health and disease.");
        NUSModule nusModule77 = new NUSModule("Clinical Pharmacology and Therapeutics", "MDP4101", "This module covers the principles and practice of drug therapy in clinical settings, including drug interactions, adverse effects, and patient monitoring.");
        NUSModule nusModule78 = new NUSModule("Clinical Skills", "MDP4102", "This module focuses on developing practical clinical skills, including physical examination, diagnostic reasoning, and communication with patients.");
        NUSModule nusModule79 = new NUSModule("Medical Research Methods", "MDP4103", "This module introduces students to the basic principles and methods of medical research, including study design, data analysis, and ethical considerations.");

        Long nusModId70 = nUSModuleSessionBean.createNUSModule(nusModule70, facultyId8);
        Long nusModId71 = nUSModuleSessionBean.createNUSModule(nusModule71, facultyId8);
        Long nusModId72 = nUSModuleSessionBean.createNUSModule(nusModule72, facultyId8);
        Long nusModId73 = nUSModuleSessionBean.createNUSModule(nusModule73, facultyId8);
        Long nusModId74 = nUSModuleSessionBean.createNUSModule(nusModule74, facultyId8);
        Long nusModId75 = nUSModuleSessionBean.createNUSModule(nusModule75, facultyId8);
        Long nusModId76 = nUSModuleSessionBean.createNUSModule(nusModule76, facultyId8);
        Long nusModId77 = nUSModuleSessionBean.createNUSModule(nusModule77, facultyId8);
        Long nusModId78 = nUSModuleSessionBean.createNUSModule(nusModule78, facultyId8);
        Long nusModId79 = nUSModuleSessionBean.createNUSModule(nusModule79, facultyId8);

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

        NUSModule nusModule80 = new NUSModule("Chemistry for Life Sciences", "CM1121", "This module provides an introduction to the principles and applications of chemistry in the context of life sciences. Topics covered include atomic and molecular structure, chemical bonding, thermodynamics, and organic chemistry.");
        NUSModule nusModule81 = new NUSModule("Mathematics I", "MA1100", "This module provides an introduction to calculus and linear algebra. Topics covered include limits, derivatives, integrals, differential equations, matrices, and systems of linear equations.");
        NUSModule nusModule82 = new NUSModule("Mathematics II", "MA1101R", "This module builds on the concepts and techniques introduced in Mathematics I. Topics covered include multivariable calculus, vector calculus, and Fourier series and transforms.");
        NUSModule nusModule83 = new NUSModule("Physics I", "PC1141", "This module provides an introduction to classical mechanics, waves, and thermodynamics. Topics covered include kinematics, forces, energy, momentum, oscillations, waves, and thermodynamics.");
        NUSModule nusModule84 = new NUSModule("Physics II", "PC1142", "This module builds on the concepts and techniques introduced in Physics I. Topics covered include electromagnetism, optics, and modern physics.");
        NUSModule nusModule85 = new NUSModule("Biology: Basic Principles and Techniques", "LSM1102", "This module provides an introduction to the basic principles and techniques of biology. Topics covered include the structure and function of cells, biomolecules, genetics, and evolution.");
        NUSModule nusModule86 = new NUSModule("Fundamentals of Materials Science and Engineering", "EG1311", "This module provides an introduction to the structure, properties, and processing of materials. Topics covered include crystal structure, defects, phase diagrams, mechanical properties, and thermal properties.");
        NUSModule nusModule87 = new NUSModule("Introduction to Data Science", "DSA1101", "This module provides an introduction to the principles and techniques of data science. Topics covered include data manipulation, data visualization, statistical inference, and machine learning.");
        NUSModule nusModule88 = new NUSModule("Environmental Science", "GES1002", "This module provides an introduction to the scientific study of the environment. Topics covered include the earth's atmosphere, hydrosphere, and lithosphere, and the interactions between human activities and the environment.");
        NUSModule nusModule89 = new NUSModule("Introduction to Computational Thinking", "CS1010S", "This module provides an introduction to computational thinking and programming. Topics covered include problem-solving strategies, algorithms, data structures, and programming in a high-level language.");

        Long nusModId80 = nUSModuleSessionBean.createNUSModule(nusModule80, facultyId9);
        Long nusModId81 = nUSModuleSessionBean.createNUSModule(nusModule81, facultyId9);
        Long nusModId82 = nUSModuleSessionBean.createNUSModule(nusModule82, facultyId9);
        Long nusModId83 = nUSModuleSessionBean.createNUSModule(nusModule83, facultyId9);
        Long nusModId84 = nUSModuleSessionBean.createNUSModule(nusModule84, facultyId9);
        Long nusModId85 = nUSModuleSessionBean.createNUSModule(nusModule85, facultyId9);
        Long nusModId86 = nUSModuleSessionBean.createNUSModule(nusModule86, facultyId9);
        Long nusModId87 = nUSModuleSessionBean.createNUSModule(nusModule87, facultyId9);
        Long nusModId88 = nUSModuleSessionBean.createNUSModule(nusModule88, facultyId9);
        Long nusModId89 = nUSModuleSessionBean.createNUSModule(nusModule89, facultyId9);

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
        PU pu6 = new PU("Stanford University",
                "Stanford University is a private research university located in Stanford, California. It was founded in 1885 by Leland and Jane Stanford in memory of their only child, Leland Stanford Jr., who had died of typhoid fever at age 15.",
                "data:image/png;base64,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"
        );

        PU pu7 = new PU("Massachusetts Institute of Technology",
                "The Massachusetts Institute of Technology (MIT) is a private land-grant research university in Cambridge, Massachusetts. The institute has an urban campus that extends more than a mile alongside the Charles River.",
                "https://upload.wikimedia.org/wikipedia/en/thumb/4/44/MIT_Seal.svg/800px-MIT_Seal.svg.png"
        );

        PU pu8 = new PU("California Institute of Technology",
                "The California Institute of Technology (Caltech) is a private research university in Pasadena, California. The university is known for its strength in science and engineering, and is one of the world's top research universities.",
                "https://upload.wikimedia.org/wikipedia/en/thumb/a/a4/Seal_of_the_California_Institute_of_Technology.svg/1200px-Seal_of_the_California_Institute_of_Technology.svg.png"
        );

        PU pu9 = new PU("Harvard University",
                "Harvard University is a private Ivy League research university in Cambridge, Massachusetts. Established in 1636 and named for its first benefactor, clergyman John Harvard, Harvard is the oldest institution of higher learning in the United States.",
                "https://upload.wikimedia.org/wikipedia/en/thumb/2/29/Harvard_shield_wreath.svg/1200px-Harvard_shield_wreath.svg.png"
        );

        PU pu10 = new PU("Princeton University",
                "Princeton University is a private Ivy League research university in Princeton, New Jersey. Founded in 1746 in Elizabeth as the College of New Jersey, Princeton is the fourth-oldest institution of higher education in the United States and one of the nine colonial colleges chartered before the American Revolution.",
                "data:image/png;base64,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"
        );

        PU pu11 = new PU("University of Cambridge",
                "The University of Cambridge is a collegiate research university in Cambridge, United Kingdom. Founded in 1209 and granted a royal charter by King Henry III in 1231, Cambridge is the second-oldest university in the English-speaking world and the world's fourth-oldest surviving university.",
                "https://brandslogos.com/wp-content/uploads/images/large/university-of-cambridge-logo-1.png"
        );
        PU pu12 = new PU("ETH Zurich",
                "ETH Zurich is a science, technology, engineering, and mathematics university in the city of Zürich, Switzerland. It was founded in 1855 as the Swiss Federal Polytechnic School, and is considered one of the most prestigious universities in the world in these fields.",
                "data:image/png;base64,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"
        );

        PU pu13 = new PU("University of Tokyo",
                "The University of Tokyo, abbreviated as Todai or UTokyo, is a public research university located in Tokyo, Japan. Established in 1877 as the first imperial university, it is one of Japan's most prestigious universities, and is considered to be one of the top universities in the world.",
                "https://upload.wikimedia.org/wikipedia/commons/c/c2/UnivOfTokyo_logo.svg"
        );

        PU pu14 = new PU("Tsinghua University",
                "Tsinghua University is a major research university in Beijing, China, and a member of the elite C9 League of Chinese universities. It was established in 1911 as a preparatory school for students studying abroad, and has since grown to become one of China's most prestigious universities.",
                "https://www.sist.tsinghua.edu.cn/sisten/images/2.png"
        );

        PU pu15 = new PU("University of California, Berkeley",
                "The University of California, Berkeley is a public research university located in Berkeley, California. It was founded in 1868 and is the flagship institution of the ten research universities affiliated with the University of California system.",
                "https://logos-world.net/wp-content/uploads/2022/02/UC-Berkeley-Symbol.png"
        );
        PU pu16 = new PU("University of Chicago",
                "The University of Chicago is a private research university located in Chicago, Illinois. It was founded in 1890 as the University of Chicago by John D. Rockefeller, and has since grown to become one of the most prestigious universities in the United States and the world.",
                "https://upload.wikimedia.org/wikipedia/commons/c/cd/University_of_Chicago_Coat_of_arms.png"
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
        Long pId12 = pUSessionBean.createNewPu(pu12, cId2);
        Long pId13 = pUSessionBean.createNewPu(pu13, cId3);
        Long pId14 = pUSessionBean.createNewPu(pu14, cId3);
        Long pId15 = pUSessionBean.createNewPu(pu15, cId4);
        Long pId16 = pUSessionBean.createNewPu(pu16, cId4);


        PUReview review1 = new PUReview(4l, "I had a great experience at this university. The professors were knowledgeable and supportive, and the campus was beautiful. However, the administrative processes could be more streamlined.");

        PUReview review2 = new PUReview(3l, "Overall, I had a positive experience at this university. The classes were interesting and the campus was nice, but the facilities could use some updating.");

        PUReview review3 = new PUReview(2l, "I was disappointed with my experience at this university. The professors seemed disinterested and the campus was outdated. However, the location was convenient.");

        PUReview review4 = new PUReview(5l, "This university provided me with an incredible education and a wealth of opportunities. The faculty and staff were dedicated and supportive, and the campus was modern and well-equipped.");

        PUReview review5 = new PUReview(1l, "I would not recommend this university. The professors were unresponsive and the facilities were run-down. Save your money and go elsewhere.");

        PUReview review6 = new PUReview(4l, "I loved attending this university. The classes were challenging but rewarding, and the campus was vibrant and diverse.");

        PUReview review7 = new PUReview(2l, "I was disappointed with my experience at this university. The facilities were outdated and the campus lacked a sense of community. However, the professors were knowledgeable.");

        PUReview review8 = new PUReview(5l, "This university exceeded my expectations in every way. The professors were inspiring and the facilities were state-of-the-art. I am so grateful for my time here.");

        PUReview review9 = new PUReview(3l, "My experience at this university was mixed. Some of the professors were great, but others were unengaged. The facilities were adequate but could use some updating.");

        PUReview review10 = new PUReview(4l, "This university provided me with an exceptional education and countless opportunities to get involved on campus. I would highly recommend it.");

        PUReview review11 = new PUReview(1l, "I had a terrible experience at this university. The professors were uninterested and the facilities were run-down. I regret my decision to attend.");

        PUReview review12 = new PUReview(3l, "Overall, my experience at this university was positive. The classes were challenging and the campus was nice, but some of the administrative processes were confusing.");

        PUReview review13 = new PUReview(5l, "I had a fantastic experience at this university. The professors were knowledgeable and engaging, and the campus was well-maintained. I am proud to be an alumnus.");

        PUReview review14 = new PUReview(2l, "I was not impressed with this university. The professors seemed uninterested and the facilities were outdated. I would recommend looking elsewhere.");

        PUReview review15 = new PUReview(4l, "This university provided me with a great education and plenty of opportunities to get involved on campus. The faculty and staff were supportive and encouraging.");

        PUReview review16 = new PUReview(3l, "My experience at this university was mixed. Some of the professors were excellent, but others were unimpressive. The campus was nice but could use some updating.");

        PUReview review17 = new PUReview(5l, "I absolutely loved attending this university. The professors were passionate and engaging, and the campus was full of opportunities to learn and grow.");

        PUReview review18 = new PUReview(2l, "I would not recommend this university. The professors seemed uninterested and the facilities were outdated. Save your money and go elsewhere.");

        PUReview review19 = new PUReview(3l, "I had a great experience studying at this university. The professors were knowledgeable and helpful, and the campus was beautiful.");

        PUReview review20 = new PUReview(3l, "The workload at this university was extremely heavy, but I learned a lot and felt well-prepared for my career.");

        PUReview review21 = new PUReview(4l, "I loved the sense of community at this university. There were always events and activities to participate in, and I made some great friends.");

        PUReview review22 = new PUReview(4l, "The dorms at this university were not great. They were small and poorly maintained, and the food in the dining hall was not very good.");

        PUReview review23 = new PUReview(5l, "I had a fantastic time studying abroad through this university's program. It was a life-changing experience.");

        PUReview review24 = new PUReview(5l, "The career center at this university was extremely helpful in finding me an internship and eventually a job after graduation.");

        PUReview review25 = new PUReview(3l, "The professors at this university were hit or miss. Some were excellent, but others seemed to be phoning it in.");

        PUReview review26 = new PUReview(4l, "The location of this university was perfect for me. It was close to everything I needed, and there was always something to do in the surrounding area.");

        PUReview review27 = new PUReview(2l, "I found the coursework at this university to be extremely challenging, but I felt well-prepared for my field after graduation.");

        PUReview review28 = new PUReview(1l, "The campus at this university was beautiful, but the administration was not very helpful or organized.");

        PUReview review29 = new PUReview(3l, "I had a great time participating in extracurricular activities at this university. They were well-organized and diverse.");

        PUReview review30 = new PUReview(3l, "The classes at this university were often overcrowded, and it was difficult to get the attention I needed from the professors.");

        PUReview review31 = new PUReview(4l, "The financial aid process at this university was straightforward and easy to navigate.");

        PUReview review32 = new PUReview(3l, "The facilities at this university were top-notch, and I always felt safe and comfortable on campus.");

        PUReview review33 = new PUReview(5l, "I had a great experience with the study abroad program at this university. The staff were extremely helpful and supportive.");

        PUReview review34 = new PUReview(2l, "The dorms at this university were pretty average, but the dining hall had some great options.");

        PUReview review35 = new PUReview(4l, "I found the coursework at this university to be challenging, but the professors were always available to help.");

        PUReview review36 = new PUReview(1l, "The campus at this university was not very diverse, and I sometimes felt out of place as a minority student.");

        PUReview review37 = new PUReview(2l, "The extracurricular activities at this university were not well-organized, and I didn't find many that interested me.");

        PUReview review38 = new PUReview(2l, "I had some issues with the administration at this university, but overall I enjoyed my experience and felt well-prepared for my career.");

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

        PUModule module1 = new PUModule("Introduction to Programming", "CS101", "This module covers the basics of programming with Java.");
        PUModule module2 = new PUModule("Data Structures and Algorithms", "CS201", "This module explores various data structures and algorithms used in programming.");
        PUModule module3 = new PUModule("Database Systems", "CS301", "This module covers the design and implementation of database systems.");
        PUModule module4 = new PUModule("Artificial Intelligence", "CS401", "This module covers the fundamentals of artificial intelligence and machine learning.");
        PUModule module5 = new PUModule("Computer Networks", "CS501", "This module covers the concepts and protocols used in computer networks.");
        PUModule module6 = new PUModule("Software Engineering", "CS601", "This module covers the various stages of software development and project management.");
        PUModule module7 = new PUModule("Web Development", "CS701", "This module covers the development of web applications using HTML, CSS, and JavaScript.");
        PUModule module8 = new PUModule("Computer Graphics", "CS801", "This module covers the principles and techniques of computer graphics and visualisation.");
        PUModule module9 = new PUModule("Operating Systems", "CS901", "This module covers the design and implementation of operating systems.");
        PUModule module10 = new PUModule("Parallel and Distributed Computing", "CS1001", "This module covers the fundamentals of parallel and distributed computing.");

        PUModule module11 = new PUModule("Introduction to History", "HIS101", "An introduction to the study of history and its key concepts and methods.");
        PUModule module12 = new PUModule("Philosophy of Art", "ART102", "An examination of the major philosophical debates surrounding art and aesthetics.");
        PUModule module13 = new PUModule("Introduction to Linguistics", "LIN101", "An introduction to the study of language and its structure and use.");
        PUModule module14 = new PUModule("Creative Writing", "ENG102", "An exploration of creative writing techniques and styles.");
        PUModule module15 = new PUModule("Sociology of Health and Illness", "SOC102", "An examination of the social factors that shape health and illness.");
        PUModule module16 = new PUModule("Environmental Studies", "ENV101", "An exploration of the relationship between humans and the environment.");
        PUModule module17 = new PUModule("Philosophy of Mind", "PHI101", "An introduction to the major theories and debates in the philosophy of mind.");
        PUModule module18 = new PUModule("History of Music", "MUS101", "An introduction to the history of music and its major composers and styles.");
        PUModule module19 = new PUModule("Global Politics", "POL102", "An examination of the major political issues and actors on the global stage.");
        PUModule module20 = new PUModule("Religious Studies", "REL101", "An exploration of the role of religion in society and its impact on culture and politics.");

        PUModule module21 = new PUModule("Marketing Management", "BUS101", "Introduction to marketing principles and practices.");
        PUModule module22 = new PUModule("Corporate Finance", "BUS102", "Study of financial decision making in corporations.");
        PUModule module23 = new PUModule("Organizational Behavior", "BUS103", "An examination of individual, group and organizational behavior in the workplace.");
        PUModule module24 = new PUModule("Operations Management", "BUS104", "Introduction to the management of production and operations.");
        PUModule module25 = new PUModule("Business Ethics", "BUS105", "Examines ethical issues in business, including corporate social responsibility.");
        PUModule module26 = new PUModule("International Business", "BUS106", "Explores the international business environment and its effects on business operations.");
        PUModule module27 = new PUModule("Business Law", "BUS107", "Provides an understanding of the legal aspects of business transactions.");
        PUModule module28 = new PUModule("Entrepreneurship", "BUS108", "Covers the basic principles and practices of entrepreneurship.");
        PUModule module29 = new PUModule("Human Resource Management", "BUS109", "An overview of human resource management principles and practices.");
        PUModule module30 = new PUModule("Economics for Business", "BUS110", "An introduction to microeconomics and macroeconomics for business decision making.");

        PUModule module31 = new PUModule("Dental Anatomy and Occlusion", "DEN201", "This module covers the anatomy of the teeth and jaws, as well as occlusion and the principles of dental occlusion.");
        PUModule module32 = new PUModule("Dental Pharmacology and Therapeutics", "DEN202", "This module covers the pharmacology and therapeutics of drugs used in dental practice, including local and general anaesthetics, analgesics, and antibiotics.");
        PUModule module33 = new PUModule("Oral Microbiology and Immunology", "DEN301", "This module covers the microbiology and immunology of the oral cavity, including the normal flora, opportunistic pathogens, and the host response to infection.");
        PUModule module34 = new PUModule("Oral Medicine and Pathology", "DEN302", "This module covers the diagnosis, management, and treatment of oral diseases, including oral cancer, salivary gland disorders, and oral infections.");
        PUModule module35 = new PUModule("Oral Radiology and Imaging", "DEN401", "This module covers the principles and techniques of dental radiology and imaging, including intraoral and extraoral radiographs, panoramic radiography, and cone beam computed tomography.");
        PUModule module36 = new PUModule("Restorative Dentistry", "DEN402", "This module covers the principles and techniques of restorative dentistry, including the use of direct and indirect restorations, dental materials, and the management of dental caries.");
        PUModule module37 = new PUModule("Endodontics", "DEN501", "This module covers the diagnosis and management of pulpal and periapical diseases, including root canal therapy, apexification, and apexogenesis.");
        PUModule module38 = new PUModule("Periodontics", "DEN502", "This module covers the diagnosis and management of periodontal diseases, including gingivitis, periodontitis, and periodontal surgery.");
        PUModule module39 = new PUModule("Prosthodontics", "DEN601", "This module covers the principles and techniques of prosthodontics, including the use of complete and partial dentures, fixed prosthodontics, and implant dentistry.");

        PUModule module40 = new PUModule("Sustainable Building Design", "DES101", "Introduction to sustainable design principles for buildings.");
        PUModule module41 = new PUModule("Environmental Design", "DES102", "Examination of the impact of the built environment on human health and well-being.");
        PUModule module42 = new PUModule("Landscape Architecture", "DES103", "Introduction to the principles and practices of landscape architecture.");
        PUModule module43 = new PUModule("Urban Planning", "DES104", "Overview of urban planning concepts, policies, and practices.");
        PUModule module44 = new PUModule("Design Thinking", "DES105", "Introduction to the design thinking process and its applications in various fields.");
        PUModule module45 = new PUModule("Architecture and Society", "DES106", "Exploration of the social and cultural contexts of architecture and the built environment.");
        PUModule module46 = new PUModule("Sustainable Urbanism", "DES107", "Examination of sustainable urban design practices and strategies.");
        PUModule module47 = new PUModule("Building Performance Analysis", "DES108", "Introduction to performance analysis tools for buildings.");
        PUModule module48 = new PUModule("Design and Technology", "DES109", "Exploration of the relationship between design and technology in various fields.");
        PUModule module49 = new PUModule("Design Research Methods", "DES110", "Introduction to research methods for design and its applications in various fields.");

        PUModule module50 = new PUModule("Introduction to Computer Science", "ENG1001", "This module introduces students to the basic concepts and techniques of computer science, including programming languages, algorithms, data structures, and computer systems.");
        PUModule module51 = new PUModule("Digital Electronics", "ENG1002", "This module covers the principles and applications of digital electronics, including Boolean algebra, combinational and sequential circuits, and memory devices.");
        PUModule module52 = new PUModule("Engineering Mathematics I", "ENG1003", "This module provides an introduction to the fundamental concepts of mathematics used in engineering, including calculus, linear algebra, and differential equations.");
        PUModule module53 = new PUModule("Mechanics and Materials", "ENG1004", "This module covers the basic principles of mechanics and materials science, including forces, moments, stress, strain, and deformation.");
        PUModule module54 = new PUModule("Engineering Design and Innovation", "ENG1005", "This module introduces students to the process of engineering design and innovation, including problem identification, concept generation, prototyping, and evaluation.");
        PUModule module55 = new PUModule("Signals and Systems", "ENG1006", "This module covers the fundamental concepts and techniques of signals and systems, including Fourier series and transforms, Laplace transforms, and system analysis.");
        PUModule module56 = new PUModule("Engineering Mathematics II", "ENG1007", "This module builds upon the mathematical concepts covered in Engineering Mathematics I, including vector calculus, Fourier analysis, and partial differential equations.");
        PUModule module57 = new PUModule("Circuits and Devices", "ENG1008", "This module covers the principles and applications of circuits and devices, including amplifiers, filters, oscillators, and transistors.");
        PUModule module58 = new PUModule("Thermodynamics and Heat Transfer", "ENG1009", "This module introduces students to the principles and applications of thermodynamics and heat transfer, including laws of thermodynamics, energy conversion, and heat transfer mechanisms.");
        PUModule module59 = new PUModule("Materials Science and Engineering", "ENG1010", "This module covers the fundamental principles of materials science and engineering, including crystal structure, defects, properties, and applications.");

        PUModule module60 = new PUModule("Criminal Law", "LAW101", "This module provides an introduction to criminal law and its foundational concepts.");
        PUModule module61 = new PUModule("Tort Law", "LAW102", "This module covers the law of torts and its practical application in various contexts.");
        PUModule module62 = new PUModule("Contract Law", "LAW103", "This module explores the law of contracts and its role in shaping commercial transactions.");
        PUModule module63 = new PUModule("Property Law", "LAW104", "This module provides an overview of property law and its historical development.");
        PUModule module64 = new PUModule("Constitutional Law", "LAW105", "This module covers the principles of constitutional law and their application in legal practice.");
        PUModule module65 = new PUModule("Legal Writing and Research", "LAW106", "This module teaches the skills required for legal research and writing.");
        PUModule module66 = new PUModule("Human Rights Law", "LAW107", "This module examines the legal framework for human rights and its impact on contemporary issues.");
        PUModule module67 = new PUModule("Intellectual Property Law", "LAW108", "This module explores the principles of intellectual property law and their application in practice.");
        PUModule module68 = new PUModule("International Law", "LAW109", "This module provides an introduction to international law and its role in shaping global relations.");
        PUModule module69 = new PUModule("Corporate Law", "LAW110", "This module covers the law of corporations and their governance.");

        PUModule module70 = new PUModule("Human Anatomy", "MED101", "This module provides an in-depth understanding of the human anatomy, covering various systems and organs in the body.");
        PUModule module71 = new PUModule("Pathophysiology", "MED102", "This module covers the pathophysiological basis of common diseases and their diagnosis, treatment and management.");
        PUModule module72 = new PUModule("Medical Ethics", "MED103", "This module explores ethical issues in medicine, such as patient autonomy, informed consent, confidentiality, and end-of-life care.");
        PUModule module73 = new PUModule("Pharmacology", "MED104", "This module covers the principles of pharmacology and the actions and uses of drugs in the treatment of various diseases.");
        PUModule module74 = new PUModule("Medical Microbiology", "MED105", "This module provides an understanding of the biology of microorganisms and their role in infectious diseases.");
        PUModule module75 = new PUModule("Medical Imaging", "MED106", "This module covers the principles of medical imaging and its application in diagnosis and treatment of diseases.");
        PUModule module76 = new PUModule("Clinical Skills", "MED107", "This module focuses on the development of clinical skills in history taking, physical examination, and communication with patients.");
        PUModule module77 = new PUModule("Community Medicine", "MED108", "This module covers the principles of community medicine and public health, including disease prevention and health promotion.");
        PUModule module78 = new PUModule("Clinical Epidemiology", "MED109", "This module covers the principles of clinical epidemiology and research methodology, including study design and data analysis.");
        PUModule module79 = new PUModule("Surgery", "MED110", "This module provides an introduction to surgical principles and techniques, including basic surgical skills and procedures.");

        PUModule module80 = new PUModule("Machine Learning for Science", "SC9101", "This module introduces machine learning techniques for analyzing scientific data, including classification, clustering, and regression.");
        PUModule module81 = new PUModule("Introduction to Genetics", "SC1101", "This module introduces students to the basic principles of genetics and how they apply to various fields of science.");
        PUModule module82 = new PUModule("Environmental Science", "SC2201", "This module covers the fundamental concepts of environmental science and how they relate to current environmental issues.");
        PUModule module83 = new PUModule("Biostatistics", "SC3101", "This module provides students with a foundation in statistical methods for biological research.");
        PUModule module84 = new PUModule("Astrophysics", "SC4201", "This module explores the physical principles that govern the behavior of the universe, from stars and galaxies to black holes and dark matter.");
        PUModule module85 = new PUModule("Computational Biology", "SC5101", "This module introduces students to computational methods for analyzing biological data, including gene sequencing and protein structure prediction.");
        PUModule module86 = new PUModule("Nanotechnology", "SC6201", "This module explores the unique properties of materials at the nanoscale and their potential applications in fields such as electronics, medicine, and energy.");
        PUModule module87 = new PUModule("Environmental Chemistry", "SC7201", "This module covers the chemistry of natural and anthropogenic processes that affect the environment, including atmospheric chemistry and water pollution.");
        PUModule module88 = new PUModule("Quantum Mechanics", "SC8201", "This module provides an introduction to the principles of quantum mechanics, including wave-particle duality, uncertainty, and entanglement.");

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

//        PUReview puReview1 = new PUReview(new Long(5), pId1);
//        PUReview puReview2 = new PUReview(new Long(4), pId1);
//        PUReview puReview3 = new PUReview(new Long(3), pId1);
//        PUReview puReview4 = new PUReview(new Long(5), pId1);
//        PUReview puReview5 = new PUReview(new Long(5), pId1);
//
//        PUReview puReview6 = new PUReview(new Long(5), pId2);
//        PUReview puReview7 = new PUReview(new Long(4), pId2);
//        PUReview puReview8 = new PUReview(new Long(4), pId2);
//        PUReview puReview9 = new PUReview(new Long(3), pId2);
//        PUReview puReview10 = new PUReview(new Long(3), pId2);
//
//        PUReview puReview11 = new PUReview(new Long(3), pId3);
//        PUReview puReview12 = new PUReview(new Long(3), pId3);
//        PUReview puReview13 = new PUReview(new Long(4), pId3);
//        PUReview puReview14 = new PUReview(new Long(3), pId3);
//        PUReview puReview15 = new PUReview(new Long(3), pId3);
//
//        PUReview puReview16 = new PUReview(new Long(4), pId4);
//        PUReview puReview17 = new PUReview(new Long(4), pId4);
//        PUReview puReview18 = new PUReview(new Long(5), pId4);
//        PUReview puReview19 = new PUReview(new Long(5), pId4);
//        PUReview puReview20 = new PUReview(new Long(5), pId4);
//
//        PUReview puReview21 = new PUReview(new Long(2), pId5);
//        PUReview puReview22 = new PUReview(new Long(2), pId5);
//        PUReview puReview23 = new PUReview(new Long(5), pId5);
//        PUReview puReview24 = new PUReview(new Long(5), pId5);
//        PUReview puReview25 = new PUReview(new Long(5), pId5);
//        
//        puReview1.setIsInappropriate(true);
//        puReview2.setIsInappropriate(true);
//        puReview3.setIsInappropriate(true);
//        puReview4.setIsInappropriate(true);
//        puReview5.setIsInappropriate(true);
        ForumTopic forumTopic1 = new ForumTopic("WTF Topic");
        ForumTopic forumTopic2 = new ForumTopic("CCB Topic");
        ForumTopic forumTopic3 = new ForumTopic("CHAO Topic");
        ForumTopic forumTopic4 = new ForumTopic("Fourth Topic");
        ForumTopic forumTopic5 = new ForumTopic("Fifth Topic");

        forumTopic1.setIsInappropriate(true);
        forumTopic2.setIsInappropriate(true);
        forumTopic3.setIsInappropriate(true);
        
        forumTopicSessionBeanLocal.createNewForumTopic(forumTopic1, studentId1, pId1);
        forumTopicSessionBeanLocal.createNewForumTopic(forumTopic2, studentId2, pId2);
        forumTopicSessionBeanLocal.createNewForumTopic(forumTopic3, studentId3, pId3);
        forumTopicSessionBeanLocal.createNewForumTopic(forumTopic4, studentId1, pId4);
        forumTopicSessionBeanLocal.createNewForumTopic(forumTopic5, studentId2, pId5);
        
        ForumPost forumPost1 = new ForumPost("This is stupid?", "Stupid PU!!");
        ForumPost forumPost2 = new ForumPost("HK nice food?", "What a joke!!");
        ForumPost forumPost3 = new ForumPost("Places to stay?", "I want to stay in Singapore");

        forumPost1.setIsInappropriate(true);
        forumPost2.setIsInappropriate(true);

        forumPost1.setNoOfLikes(0);
        forumPost1.setNoOfDislikes(0);

        forumPost2.setNoOfLikes(0);
        forumPost2.setNoOfDislikes(0);

        forumPost1.getDislikedStudents().add(student1.getStudentId());
        forumPost1.getDislikedStudents().add(student2.getStudentId());

        forumPostSessionBeanLocal.createNewForumPost(forumPost1, forumTopic2.getTopicId(), student1.getStudentId());
        forumPostSessionBeanLocal.createNewForumPost(forumPost2, forumTopic2.getTopicId(), student3.getStudentId());
        forumPostSessionBeanLocal.createNewForumPost(forumPost3, forumTopic1.getTopicId(), student2.getStudentId());

        ForumComment forumComment1 = new ForumComment("Wow this is such a fking shit!", false);
        ForumComment forumComment2 = new ForumComment("fk you uds", false);
        ForumComment forumComment3 = new ForumComment("Marina Bay Sands!", false);

        forumComment1.setIsInappropriate(true);
        forumComment2.setIsInappropriate(true);

        forumComment1.setNoOfLikes(0);
        forumComment1.setNoOfDislikes(0);

        forumComment2.setNoOfLikes(0);
        forumComment2.setNoOfDislikes(0);

        forumCommentSessionBeanLocal.createNewForumComment(forumComment1, forumPost1.getPostId(), student3.getStudentId());
        forumCommentSessionBeanLocal.createNewForumComment(forumComment2, forumPost2.getPostId(), student2.getStudentId());
        forumCommentSessionBeanLocal.createNewForumComment(forumComment3, forumPost3.getPostId(), student1.getStudentId());

//        Enquiry enquiry1 = new Enquiry("Hello", "Help");
//        Enquiry enquiry2 = new Enquiry("Bye", "World");
//        Enquiry enquiry3 = new Enquiry("Interesting", "Story");
//
//        studentEnquirySessionBeanLocal.createEnquiry(enquiry1, 1L);
//        studentEnquirySessionBeanLocal.createEnquiry(enquiry2, 1L);
//        studentEnquirySessionBeanLocal.createEnquiry(enquiry3, 2L);
    }

    private void createData() throws NoResultException {

        NUSModule nusModule1 = new NUSModule("Enterprise Systems Interface Design and Development", "IS3106", "This module aims to train students to be conversant in front-end development for Enterprise Systems. It complements IS2103 which focuses on backend development aspects for Enterprise Systems. Topics covered include web development scripting languages, web templating design and component design, integrating with backend application, and basic mobile application development.");

        NUSModule nusModule2 = new NUSModule("Computational Thinking", "CS1010", "The module provides a gentle introduction to programming using Python programming language. It aims to provide students with an understanding of the role computation can play in solving problems. It also aims to help students, regardless of their major, to feel justifiably confident of their ability to write small programs that allow them to accomplish useful goals.");

        NUSModule nusModule3 = new NUSModule("Data Structures and Algorithms", "CS2040", "This module is about the design, analysis, and implementation of data structures and algorithms. It covers classical data structures (linked lists, stacks, queues, trees, and graphs), and algorithms (sorting, searching, and graph traversal). It also includes an introduction to algorithmic complexity and intractability.");

        NUSModule nusModule4 = new NUSModule("Programming Methodology", "CS1101S", "This module aims to teach students how to program systematically, and how to develop good programming habits. It uses a variant of the Racket programming language to solidify the concepts learned in class.");

        NUSModule nusModule5 = new NUSModule("Programming Methodology II", "CS2030", "This module aims to teach students how to design and develop larger programs, and how to write programs that are more reliable, efficient, and maintainable. It covers topics such as abstraction, interfaces, data representation, and program correctness. It also includes an introduction to concurrent programming.");

        NUSModule nusModule6 = new NUSModule("Introduction to Computer Networks", "CS2105", "This module provides an introduction to computer networks, covering the layered architecture of computer networks and the services and protocols that are provided at each layer.");

        NUSModule nusModule7 = new NUSModule("Database Systems", "CS2102", "This module covers the principles and techniques of database systems. It includes topics such as data models, relational algebra, SQL, and transaction processing. It also covers topics such as query optimization, concurrency control, and recovery.");

        NUSModule nusModule8 = new NUSModule("Operating Systems", "CS2106", "This module covers the principles and concepts of operating systems, including process management, memory management, file systems, and input/output.");

        NUSModule nusModule9 = new NUSModule("Computer Security", "CS2107", "This module covers the principles and techniques of computer security. It includes topics such as access control, authentication, confidentiality, integrity, and availability. It also covers topics such as network security, web security, and malware.");

        NUSModule nusModule10 = new NUSModule("Software Engineering", "CS2103T", "This module covers the principles and practices of software engineering, including requirements elicitation, software design, implementation, testing, and maintenance. It also covers topics such as project management, software process, and software quality.");

//        PUReview pUReview1 = new PUReview(1L, "Meh", 2, 100, false);
//        PUReview pUReview2 = new PUReview(2L, "Meh", 3, 10, false);
//        PUReview pUReview3 = new PUReview(3L, "Meh", 4, 20, true);
//
//        pUReviewSessionBean.createPUReview(pUReview1, 1L);
//        pUReviewSessionBean.createPUReview(pUReview2, 1L);
//        pUReviewSessionBean.createPUReview(pUReview3, 1L);
    }

}
