import "../assets/base.scss";
import 'bootstrap/dist/css/bootstrap.min.css';
import "./AccordionComp.css";
import React from "react";
import { Accordion, Container, Table, Row, Col } from "react-bootstrap";


function AccordionComp() {
    return (
        <div style={{paddingLeft:"5%", paddingRight:"5%"}}>
            <Container fluid>
                <Row>
                    <h1 className="text-center mb-3">Academic modules for ABC University</h1>
                    <Accordion defaultActiveKey={['0']}>
                        <Accordion.Item eventKey="0">
                            <Accordion.Header as="h3">
                                School Of Computing
                            </Accordion.Header>
                            <Accordion.Body>
                                <div className="table-responsive-md">
                                    <Table hover bordered striped className="mb-5">
                                        <thead className="thead-light">
                                            <tr>
                                                <th scope="col">NUS Module</th>
                                                <th scope="col">NUS Module Description</th>
                                                <th scope="col">PU Module</th>
                                                <th scope="col">PU Module Description</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td>1</td>
                                                <td>Mark</td>
                                                <td>Otto</td>
                                                <td>@mdo</td>
                                            </tr>
                                        </tbody>
                                    </Table>
                                </div>
                            </Accordion.Body>
                        </Accordion.Item>
                    </Accordion>

                </Row>
            </Container>
        </div>
    );
}

export default AccordionComp;