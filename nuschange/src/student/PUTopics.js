import React, { Fragment} from 'react';
import { Link } from 'react-router-dom';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { library } from '@fortawesome/fontawesome-svg-core';
import { far } from '@fortawesome/free-regular-svg-icons';
import { fas } from '@fortawesome/free-solid-svg-icons';
import { faPlus } from '@fortawesome/free-solid-svg-icons';
import './styles.css';
import SearchIcon from './homepage/search.svg';

import {
  Table,
  CardBody,
  Card,
  Button,
} from 'reactstrap';

import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import ReactPaginate from 'react-paginate'

library.add(far, fas, faPlus);

export default function PUTopics() {
  const { puId, studentId } = useParams();
  const [forumTopics, setForumTopics] = useState([]);
  const [searchQuery, setSearchQuery] = useState('');

  const [pageNumber, setPageNumber] = useState(0);
  const itemsPerPage = 5; // Change this value to the number of items you want to display per page
  const pagesVisited = pageNumber * itemsPerPage;
  const pageCount = Math.ceil(forumTopics.length / itemsPerPage);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/PU-war/webresources/forumTopics/pu/${puId}`);
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
        const response = await axios.get(`http://localhost:8080/PU-war/webresources/forumTopics/query/pu/${puId}?topicName=${searchQuery}`);
        setForumTopics(response.data);
      } catch (error) {
        console.error(error);
      }
    };
    fetchData();
  }

  const handleDelete = (id) => {
    axios.delete(`http://localhost:8080/PU-war/webresources/forumTopics/${id}`)
      .then(response => {
        // Handle successful response here
        console.log('Delete successful');
      })
      .catch(error => {
        // Handle error response here
        console.error('Delete failed: ', error);
      });
  }

  const handleReport = (id) => {
    axios.put(`http://localhost:8080/PU-war/webresources/forumTopics/report/${id}`)
      .then(response => {
        // Handle successful response here
        console.log('Report successful');
      })
      .catch(error => {
        // Handle error response here
        console.error('Report failed: ', error);
      });
  }

  function getTimeDifference(timeOfCreation) {
    const now = new Date();
    const diff = (now.getTime() - new Date(timeOfCreation).getTime()) / 1000;
  
    if (diff < 60) {
      return `${Math.floor(diff)} seconds ago`;
    } else if (diff < 3600) {
      const minutes = Math.floor(diff / 60);
      const seconds = Math.floor(diff % 60);
      return `${minutes}min ${seconds} seconds ago`;
    } else if (diff < 86400) {
      const hours = Math.floor(diff / 3600);
      const minutes = Math.floor((diff % 3600) / 60);
      return `${hours} hours ${minutes} minutes ago`;
    } else {
      const days = Math.floor(diff / 86400);
      const hours = Math.floor((diff % 86400) / 3600);
      return `${days} days ${hours} hours ago`;
    }
  }

  return (
    <Fragment>
      <div className="search-container">
        <div className="search-wrapper">
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
      </div>
      <Card className="card-box mb-5">
        <div className="card-header">
          <div className="card-header--title">
            <small>Forum</small>
            <b>Forum Topics</b>
          </div>
          <div className="card-header--actions">
            <Button
              tag={Link}
              to={`/new-pu-topic/${puId}/${1}`}
              color="outline-primary"
              title="View details"
              className="mr-2">
              <FontAwesomeIcon icon={['fa', 'plus']} className="mr-1" />
              Create new Topic
            </Button>
          </div>
          <div className="card-header--actions">
            <Button
              tag={Link}
              to={`/pu-my-topics/${puId}/${1}`}
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
                  !item.isInappropriate && (
                    <tr key={item.topicId}>
                      <td>
                        <a className="font-weight-bold text-black">
                          {item.topicName}
                        </a>
                          <Link to={`/`} className="text-black-50 d-block blue-link" style={{ textDecoration: 'none' }}>
                            Author: {item.studentFirstName} {item.studentLastName}
                          </Link>
                        {item.isEdited && (
                          <span className="edited-info">
                            Last Edited: {getTimeDifference(item.lastEdit)}
                          </span>
                        )}
                      </td>
                      <td style={{ textAlign: 'center', verticalAlign: 'middle' }}>
                        <a className="font-weight-bold text-black">
                          {item.forumPosts.length}
                        </a>
                      </td>
                      {item.studentId == 1 ? (
                        <td className="text-center">
                          <Button
                            tag={Link}
                            to={`/forum-topics/${item.topicId}/${encodeURIComponent(item.topicName)}/1`}
                            size="sm"
                            color="link"
                            className="text-primary"
                            title="View"
                          >
                            <FontAwesomeIcon icon={['fa', 'eye']} className="font-size-lg eye" />
                          </Button>
              
                          <Button
                            tag={Link}
                            to={`/forum-topics/edit/${item.topicId}/${item.topicName}`}
                            size="sm"
                            color="link"
                            className="text-warning ml-2"
                            title="Edit"
                          >
                            <FontAwesomeIcon icon={['fa', 'edit']} className="font-size-lg edit" />
                          </Button>
              
                          <Button
                            onClick={() => handleDelete(item.topicId)}
                            size="sm"
                            color="link"
                            className="text-danger ml-2"
                            title="Delete"
                          >
                            <FontAwesomeIcon icon={['fa', 'trash']} className="font-size-lg delete" />
                          </Button>
                        </td>
                      ) :
                        <td className="text-center">
                          <Button
                            tag={Link}
                            to={`/forum-topics/${item.topicId}/${encodeURIComponent(item.topicName)}/1`}
                            size="sm"
                            color="link"
                            className="text-primary"
                            title="View"
                          >
                            <FontAwesomeIcon icon={['fa', 'eye']} className="font-size-lg eye" />
                          </Button>
              
                          <Button
                            onClick={() => handleReport(item.topicId)}
                            size="sm"
                            color="link"
                            className="text-secondary ml-2"
                            title="Report"
                          >
                            <FontAwesomeIcon icon={['fa', 'flag']} className="font-size-lg flag" />
                          </Button>
                        </td>
                      }
                    </tr>
                  )
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
