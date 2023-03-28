import React, { Fragment} from 'react';
// import { useNavigate } from 'react-router-dom';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { library } from '@fortawesome/fontawesome-svg-core';
import { far } from '@fortawesome/free-regular-svg-icons';
import { fas } from '@fortawesome/free-solid-svg-icons';
import { faPlus } from '@fortawesome/free-solid-svg-icons';
import './styles.css';
import SearchIcon from './search.svg';

import {
  Table,
  CardBody,
  Card,
  CardHeader,
  CustomInput,
  Badge,
  Nav,
  NavItem,
  NavLink,
  Pagination,
  PaginationItem,
  PaginationLink,
  Button,
  ButtonGroup,
  UncontrolledDropdown,
  DropdownToggle,
  DropdownMenu
} from 'reactstrap';
import { useEffect, useState } from 'react';
import axios from 'axios';
import ReactPaginate from 'react-paginate'

const API_URL = 'http://localhost:8080/PU-war/webresources/forumTopics';

library.add(far, fas, faPlus);

export default function ForumTopics() {
  const [forumTopics, setForumTopics] = useState([]);
  const [searchQuery, setSearchQuery] = useState('');

  const [pageNumber, setPageNumber] = useState(0);
  const itemsPerPage = 5; // Change this value to the number of items you want to display per page
  const pagesVisited = pageNumber * itemsPerPage;
  const pageCount = Math.ceil(forumTopics.length / itemsPerPage);
  // const navigate = useNavigate();

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get(API_URL);
        setForumTopics(response.data);
      } catch (error) {
        console.error(error);
      }
    };
    fetchData();
  }, []);

  const searchForumTopic = async (searchQuery) => {
    const fetchData = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/PU-war/webresources/forumTopics/query?topicName=${searchQuery}`);
        setForumTopics(response.data);
      } catch (error) {
        console.error(error);
      }
    };
    fetchData();
  }

  return (
    <Fragment>
      <div className="search">
        <input
          placeholder="Search for Forum Topic"
          value={searchQuery}
          onChange={(e) => setSearchQuery(e.target.value)}
        />
        <img
          src={SearchIcon}
          alt="search"
          onClick={() => searchForumTopic(searchQuery)}
        />
      </div>
      <Card className="card-box mb-5">
        <div className="card-header">
          <div className="card-header--title">
            <small>Forum</small>
            <b>Forum Topics</b>
          </div>
          <div className="card-header--actions">
            <Button
              tag="a"
              href="#/"
              onClick={(e) => e.preventDefault()}
              color="outline-primary"
              title="View details"
              className="mr-2">
              <FontAwesomeIcon icon={['fa', 'plus']} className="mr-1" />
              Create new post
            </Button>
          </div>
          <div className="card-header--actions">
            <Button
              tag="a"
              href="#/"
              onClick={(e) => e.preventDefault()}
              color="outline-primary"
              title="View My Topics"
              className="ml-2">
              <FontAwesomeIcon icon={['fa', 'eye']} className="mr-1" />
              View My Topics
            </Button>
          </div>
        </div> 
        <CardBody className="p-0">
          <div className="table-responsive-md">
            <Table hover striped className="text-nowrap mb-0">
              <thead className="thead-light">
                <tr>
                  <th style={{ width: '40%' }}>Topics</th>
                  <th className="text-center">Number of Posts</th>
                  <th className="text-center">Actions</th>
                </tr>
              </thead>
              {forumTopics.length > 0 ? (
              <tbody>
                {forumTopics.slice(pagesVisited, pagesVisited + itemsPerPage).map((item) => (
                  <tr key={item.id}>
                    <td>
                      <a className="font-weight-bold text-black">
                        {item.topicName}
                      </a>
                    </td>
                    <td style={{ textAlign: 'center', verticalAlign: 'middle' }}>
                      <a className="font-weight-bold text-black">
                        {item.forumPosts.length}
                      </a>
                    </td>
                    <td className="text-center">
                      <Button
                        tag="a"
                        href="#/"
                        // onClick={(e) => {
                        //   e.preventDefault();
                        //   navigate(`/post/:id`, { state: { topic: item.id } });
                        // }}
                        size="sm"
                        color="link"
                        className="text-primary"
                        title="View"
                      >
                        <FontAwesomeIcon icon={['fa', 'eye']} className="font-size-lg" />
                      </Button>
                    </td>
                  </tr>
                ))}
              </tbody>
              ) : (
                <tbody>
                  <tr>
                    <td colSpan="3" className="text-center">
                      No topics yet
                    </td>
                  </tr>
                </tbody>
              )} 
            </Table>
          </div>
          <div className="divider" />
          <div className="divider" />
          <div className="p-3 d-flex justify-content-center">
            <ReactPaginate
              previousLabel={<FontAwesomeIcon icon={['fas', 'chevron-left']} />}
              nextLabel={<FontAwesomeIcon icon={['fas', 'chevron-right']} />}
              breakLabel={'...'}
              breakClassName={'page-item'}
              breakLinkClassName={'page-link'}
              pageCount={pageCount}
              marginPagesDisplayed={2}
              pageRangeDisplayed={5}
              onPageChange={({ selected }) => setPageNumber(selected)}
              containerClassName={'pagination pagination-primary'}
              activeClassName={'active'}
            />
          </div>
        </CardBody>
      </Card>
    </Fragment>
  );
}
