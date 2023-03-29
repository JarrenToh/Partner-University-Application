import React from "react";
import { Accordion, Container } from "react-bootstrap";

const FAQPage = () => {
  const dummyFAQ = [
    {
      faqId: 1,
      answer: "Hmmmm",
      created: "12/3/2023",
      question: "How is IS3106?",
      created_by: 1,
    },
    {
      faqId: 2,
      answer: "Hmmmm",
      created: "12/3/2023",
      question: "How is your life?",
      created_by: 1,
    },
    {
      faqId: 3,
      answer: "You click on Mappable Modules",
      created: "12/3/2023",
      question: "How to find mappable modules?",
      created_by: 2,
    },
    {
      faqId: 4,
      answer: "I ain't got no clue too",
      created: "12/3/2023",
      question: "How do I contact the alumnis from a Partner University?",
      created_by: 2,
    },
    {
      faqId: 5,
      answer: "Go to telegram and change",
      created: "12/3/2023",
      question: "How do I edit my telegram handle?",
      created_by: 2,
    },
  ];

  return (
    <div >
      <div className="container">
        <h1>Frequently Asked Questions (FAQ)</h1>
      </div>
      <div style={{paddingLeft:"5%", paddingRight:"5%"}}>
        <Accordion flush>
          {dummyFAQ.map((faq) => (
            <Accordion.Item eventKey={faq.faqId}>
              <Accordion.Header>{faq.question}</Accordion.Header>
              <Accordion.Body>{faq.answer}.</Accordion.Body>
            </Accordion.Item>
          ))}
        </Accordion>
      </div>
    </div>
  );
};

export default FAQPage;
