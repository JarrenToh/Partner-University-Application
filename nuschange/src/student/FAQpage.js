import React, { useState, useEffect } from "react";
import { Accordion, Card, Container } from "react-bootstrap";
import apiPaths from "../util/apiPaths";

const FAQPage = () => {
  const [faqs, setFAQs] = useState([]);

  useEffect(() => {
    searchFAQs("");
  }, []);

  const searchFAQs = async (title) => {
    const response = await fetch(apiPaths.listOfFaqs);
    const data = await response.json();
    console.log(data);
    setFAQs(data);
  };

  return (
    <div>
      <div className="container" style={{ border: 0 }}>
        <h1 className="text-center mb-3">Frequently Asked Questions</h1>
      </div>
      <div style={{ paddingLeft: "5%", paddingRight: "5%" }}>
        
          <Accordion>
            {faqs.map((faq) => (
              <Accordion.Item eventKey={faq.faqId}>
                <Accordion.Header as="h3">
                <p class="fs-5">{faq.question}</p>
                  </Accordion.Header>
                <Accordion.Body>{faq.answer}.</Accordion.Body>
              </Accordion.Item>
            ))}
          </Accordion>
        
      </div>
    </div>
  );
};

export default FAQPage;
