import React, { useState, useEffect } from 'react';
import { Bar } from 'react-chartjs-2';

import API from '../../../../util/API';
import apiPaths from '../../../../util/apiPaths';

import styles from '../../dashboard/Dashboard.module.css';

const InappropriatenessContent = () => {

    const [chartData, setChartData] = useState({});

    useEffect(() => {
        const fetchInappropriate = async (type, apiPath) => {
            try {
                const response = await API.get(apiPath);
                const dataLength = response.data.length;

                return { type, dataLength };
            } catch (error) {
                console.error(error);
            }
        };

        const initialiseChartData = (inappropriatenessContent) => {
            const jsonArray = Object.entries(inappropriatenessContent).map(([key, value]) => {
                return { name: key, count: value };
            });

            setChartData({
                labels: jsonArray.map((data) => data.name),
                datasets: [
                    {
                        data: jsonArray.map((data) => data.count),
                        backgroundColor: [
                            "rgba(75, 192, 192, 0.6)",
                            "rgba(153, 102, 255, 0.6)",
                            "rgba(255, 159, 64, 0.6)",
                            "rgba(255, 99, 132, 0.6)",
                            "rgba(54, 162, 235, 0.6)",
                        ],
                    },
                ],
            });
        }

        const fetchData = async () => {
            const requests = [
                fetchInappropriate("ForumComment", `${apiPaths.listOfForumComments}`),
                fetchInappropriate("ForumTopic", `${apiPaths.listOfForumTopics}`),
                fetchInappropriate("ForumPost", `${apiPaths.listOfForumPosts}`),
                fetchInappropriate("PUReview", `${apiPaths.listOfPUReviews}/getAllReportedPUReviews`),
                fetchInappropriate("PUModuleReview", `${apiPaths.listOfPUModuleReview}`),
            ];

            const results = await Promise.all(requests);
            const inappropriatenessContent = {};
            results.forEach(({ type, dataLength }) => {
                switch (type) {
                    case "PUReview":
                        inappropriatenessContent["Partner University Review"] = dataLength;
                        break;
                    case "ForumComment":
                        inappropriatenessContent["Forum Comment"] = dataLength;
                        break;
                    case "ForumTopic":
                        inappropriatenessContent["Forum Topic"] = dataLength;
                        break;
                    case "ForumPost":
                        inappropriatenessContent["Forum Post"] = dataLength;
                        break;
                    case "PUModuleReview":
                        inappropriatenessContent["Partner University Module Reivew"] = dataLength;
                        break;
                    default:
                        break;
                }
            });
            initialiseChartData(inappropriatenessContent);
        }

        fetchData();
    }, []);

    return (
        <div>
            <section className="col-lg-14 connectedSortable">
                <div className="card">
                    <div className="card-header">
                        <h3 className="card-title">
                            <i className="fas fa-bar-chart mr-1" />
                            Inappropriate Content
                        </h3>
                    </div>
                    <div className="card-body">
                        <div className="tab-content p-0">
                            {
                                chartData.labels && chartData.labels.length > 0 ? (
                                    <div className={styles.chartContainer}> {/* Add the chartContainer class */}
                                        <Bar
                                            data={chartData}
                                            options={{
                                                plugins: {
                                                    title: {
                                                        display: true,
                                                        text: "Number of Inappropriate Content across Categories"
                                                    },
                                                    legend: {
                                                        display: false
                                                    }
                                                },
                                                maintainAspectRatio: false,
                                                scales: {
                                                    y: {
                                                        ticks: {
                                                            callback: function (value) {
                                                                return Math.round(value); // Display only whole numbers on the y-axis
                                                            }
                                                        }
                                                    }
                                                }
                                            }}
                                        />
                                    </div>
                                ) : (
                                    <p>No data to show...</p>
                                )
                            }

                        </div>
                    </div>
                </div>
            </section>
        </div>
    )
};

export default InappropriatenessContent;