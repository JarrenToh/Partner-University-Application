import React, { Fragment, startTransition } from 'react';
import { useParams } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { library } from '@fortawesome/fontawesome-svg-core';
import { far } from '@fortawesome/free-regular-svg-icons';
import { fas } from '@fortawesome/free-solid-svg-icons';
import { faPlus } from '@fortawesome/free-solid-svg-icons';
import './styles.css';
import SearchIcon from './homepage/search.svg';
import { Link } from 'react-router-dom';

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

const API_URL = 'http://localhost:8080/PU-war/webresources/forumPosts';

library.add(far, fas, faPlus);

export default function MyPosts(props) {
  const { id, topicName, studentId } = useParams();
  const [forumPosts, setForumPosts] = useState([]);
  const [searchQuery, setSearchQuery] = useState('');
  const [pageNumber, setPageNumber] = useState(0);
  const itemsPerPage = 5; // Change this value to the number of items you want to display per page
  const pagesVisited = pageNumber * itemsPerPage;
  const pageCount = Math.ceil(forumPosts.length / itemsPerPage);

  useEffect(() => {
    const fetchData = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/PU-war/webresources/forumPosts/topic/${id}/student/${studentId}`);

            setForumPosts(response.data);
        } catch (error) {
            console.error(error);
        }
    };
    fetchData();
  }, []);

  const searchForumPost = async (searchQuery) => {
    const fetchData = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/PU-war/webresources/forumPosts/query/topic/${id}/student/${studentId}?postTitle=${searchQuery}`);
            setForumPosts(response.data);
        } catch (error) {
            console.error(error);
        }
    };
    fetchData();
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

  const handleDelete = (postId) => {
    axios.delete(`http://localhost:8080/PU-war/webresources/forumPosts/${postId}`)
        .then(response => {
            // Handle successful response here
            console.log('Delete successful');
            //setForumPosts(response.data);
        })
        .catch(error => {
            // Handle error response here
            console.error('Delete failed: ', error);
        });
  }

  return (
    <Fragment>
        <div className="search">
            <input
                placeholder="Search for Forum Post"
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
            />
            <img
                src={SearchIcon}
                alt="search"
                onClick={() => searchForumPost(searchQuery)}
            />
        </div>
        <Card className="card-box mb-5">
            <div className="card-header">
                <div className="card-header--title">
                    <small>Forum Topic: {topicName}</small>
                    <b>My Forum Posts</b>
                </div>
                <div className="card-header--actions">
                    <Button
                        tag={Link}
                        to={`/forum-posts/${id}/${topicName}`}
                        color="outline-primary"
                        title="View details"
                        className="mr-2">
                        <FontAwesomeIcon icon={['fa', 'plus']} className="mr-1" />
                        Create new post
                    </Button>
                </div>
            </div>
            <CardBody className="p-0">
                <div className="table-responsive-md">
                    <Table hover striped className="text-nowrap mb-0">
                        <thead className="thead-light">
                            <tr>
                                <th style={{ width: '40%' }}>Posts</th>
                                <th className="text-center">Posted</th>
                                <th className="text-center">Actions</th>
                            </tr>
                        </thead>
                        {forumPosts.length > 0 ? (
                            <tbody>
                                {forumPosts.slice(pagesVisited, pagesVisited + itemsPerPage).map((item) => (
                                    <tr key={item.postId}>
                                        <td>
                                            {item.isInappropriate ? (
                                                <a className="font-weight-bold text-black">
                                                    {item.title}
                                                    <FontAwesomeIcon
                                                        icon={['fa', 'circle-exclamation']}
                                                        className="font-size-lg ml-2 text-danger"
                                                        title="This forum post has been marked inappropriate by other users"
                                                    />
                                                </a>
                                            ) : (
                                                <a className="font-weight-bold text-black">
                                                    {item.title}
                                                </a>
                                            )}
                                            <Link to={`/`} className="text-black-50 d-block blue-link" style={{ textDecoration: 'none' }}>
                                                Author: Me
                                            </Link>
                                            {item.isEdited && (
                                                <span className="edited-info">
                                                    Last Edited: {getTimeDifference(item.lastEdit)}
                                                </span>
                                            )}
                                        </td>
                                        <td style={{ textAlign: 'center', verticalAlign: 'middle' }}>
                                            <div>
                                                <a onClick={(e) => e.preventDefault()} className="font-weight-bold text-black">
                                                    {getTimeDifference(item.timeOfCreation)}
                                                </a>
                                            </div>
                                        </td>
                                        <td className="text-center">
                                            <Button
                                                tag="a"
                                                href="#/"
                                                onClick={(e) => e.preventDefault()}
                                                size="sm"
                                                color="link"
                                                className="text-primary"
                                                title="View"
                                            >
                                                <FontAwesomeIcon icon={['fa', 'eye']} className="font-size-lg eye" />
                                            </Button>

                                            <Button
                                                tag={Link}
                                                to={`/forum-posts/edit/${item.postId}/${item.title}/${encodeURIComponent(item.message)}/${encodeURIComponent(topicName)}`}
                                                size="sm"
                                                color="link"
                                                className="text-warning ml-2"
                                                title="Edit"
                                            >
                                                <FontAwesomeIcon icon={['fa', 'edit']} className="font-size-lg edit" />
                                            </Button>

                                            <Button
                                                onClick={() => handleDelete(item.postId)}
                                                size="sm"
                                                color="link"
                                                className="text-danger ml-2"
                                                title="Delete"
                                            >
                                                <FontAwesomeIcon icon={['fa', 'trash']} className="font-size-lg delete" />
                                            </Button>
                                        </td>
                                    </tr>
                                ))}
                            </tbody>
                        ) : (
                            <tbody>
                                <tr>
                                    <td className="text-center" colSpan="3">
                                        No posts yet
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
