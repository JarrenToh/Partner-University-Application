import "../assets/base.scss";
import 'bootstrap/dist/css/bootstrap.min.css';
import "./AccordionComp.css";
import React from "react";
import { Accordion, Container, Table, Row, Col } from "react-bootstrap";
import { useParams } from "react-router-dom";


function AccordionComp(props) {
    const { puName } = useParams();
    return (
        <div style={{ paddingLeft: "5%", paddingRight: "5%" }}>
            <Container fluid>
                <Row>
                    <h1 className="text-center mb-3">
                        Academic modules for {puName}
                    </h1>
                    <Accordion defaultActiveKey={0} alwaysOpen>
                        {props.modules.map((mappableModule, index) => (
                            <Accordion.Item eventKey={index} key={index}>
                                <Accordion.Header as="h3">
                                    {mappableModule.facultyName}
                                </Accordion.Header>
                                <Accordion.Body>
                                    <div className="table-responsive-md">
                                        <Table hover bordered striped className="mb-5">
                                            <thead className="thead-light">
                                                <tr>
                                                    <th scope="col">NUS Module Code</th>
                                                    <th scope="col">NUS Module Description</th>
                                                    <th scope="col">PU Module Code</th>
                                                    <th scope="col">PU Module Description</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                {mappableModule.modules.map((m) => (
                                                    <tr key={m.nusCode}>
                                                        <td>{m.nusCode}</td>
                                                        <td>{m.nusDescription}</td>
                                                        <td>{m.puCode}</td>
                                                        <td>{m.puDescription}</td>
                                                    </tr>
                                                ))}
                                            </tbody>
                                        </Table>
                                    </div>
                                </Accordion.Body>
                            </Accordion.Item>
                        ))}
                    </Accordion>
                </Row>
            </Container>
        </div>
    );
}



export default AccordionComp;