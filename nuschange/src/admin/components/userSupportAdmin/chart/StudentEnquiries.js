import React, { useState, useEffect } from 'react';
import { Bar } from 'react-chartjs-2';

import API from '../../../../util/API';
import apiPaths from '../../../../util/apiPaths';

import styles from '../../dashboard/Dashboard.module.css';

const StudentEnquiries = () => {

    const [chartData, setChartData] = useState({});

    const processData = (data) => {
        const namesCount = {};

        data.forEach((item) => {
            const name = `${item.studentFirstName} ${item.studentLastName}`;
            if (namesCount[name]) {
                namesCount[name]++;
            } else {
                namesCount[name] = 1;
            }
        });

        return namesCount;
    };

    useEffect(() => {
        const fetchData = async () => {
            try {
                const apiPath = `${apiPaths.listOfEnquiries}`;
                const response = await API.get(apiPath);
                const data = response.data;

                const processedData = processData(data);
                const jsonArray = Object.entries(processedData).map(([key, value]) => {
                    return { name: key, count: value };
                });

                const top5 = jsonArray.sort((a, b) => b.count - a.count).slice(0, 5);

                setChartData({
                    labels: top5.map((data) => data.name),
                    datasets: [
                        {
                            data: top5.map((data) => data.count),
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

            } catch (error) {
                console.error(error);
            }
        };
        fetchData();
    }, []);

    return (
        <div>
            <section className="col-lg-14 connectedSortable">
                <div className="card">
                    <div className="card-header">
                        <h3 className="card-title">
                            <i className="fas fa-bar-chart mr-1" />
                            Student Enquiries
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
                                                        text: "Top 5 Student Enquiries"
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

export default StudentEnquiries;