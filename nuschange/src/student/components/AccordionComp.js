import "../assets/base.scss";
import 'bootstrap/dist/css/bootstrap.min.css';
import "./AccordionComp.css";
import React from "react";
import { Accordion, Container, Table, Row } from "react-bootstrap";
import { Link, useParams } from "react-router-dom";


function AccordionComp(props) {
    const { puName } = useParams();
    return (
        <div style={{ paddingLeft: "5%", paddingRight: "5%" }}>
            <Container fluid>
                <Row>
                    <h1 className="text-center mb-3">
                        Academic Modules For {puName.toLowerCase().split(' ').map(word => word.charAt(0).toUpperCase() + word.slice(1)).join(' ')}
                    </h1>
                    <Accordion defaultActiveKey={0} alwaysOpen >
                        {props.modules.map((faculty, index) => (
                            <Accordion.Item eventKey={index} key={index}>
                                <Accordion.Header as="h3">
                                    {faculty.facultyName}
                                </Accordion.Header>
                                <Accordion.Body>
                                    <div className="table-responsive-md">
                                        <Table hover bordered striped className="mb-5">
                                            <thead className="thead-light">
                                                <tr>
                                                    <th scope="col">NUS Module Code</th>
                                                    <th scope="col">NUS Module Name</th>
                                                    <th scope="col">PU Module Code</th>
                                                    <th scope="col">PU Module Name</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                {faculty.modules.map((nusmodule) => (
                                                    <tr key={nusmodule.code}>
                                                        <td><a href = {`https://nusmods.com/modules/${nusmodule.code}`}>{nusmodule.code}</a></td>
                                                        <td>{nusmodule.name}</td>
                                                        {nusmodule.puModules.map((pumodule) => (
                                                            <React.Fragment key={pumodule.code}>
                                                                <td><Link to = {`/module-details/${puName}/${pumodule.moduleId}`}>{pumodule.code}</Link></td>
                                                                <td>{pumodule.name}</td>
                                                            </React.Fragment>
                                                        ))}
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