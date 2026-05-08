<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Overview</title>

        <!-- Bootstrap -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">

        <!-- jQuery -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

        <!-- Chart.js -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/4.4.1/chart.umd.min.js"></script>

        <!-- Chart Data Labels -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/chartjs-plugin-datalabels/2.2.0/chartjs-plugin-datalabels.min.js"></script>

        <style>
            body{
                background-color:#f5f5f5;
            }

            .card-box{
                background:white;
                box-shadow:1px 1px 10px rgba(0,0,0,0.15);
            }
        </style>
    </head>

    <body>

        <div class="container-fluid p-3">

            <h4 class="mb-3">Overview</h4>

            <div class="p-3 d-flex justify-content-between rounded-3 card-box"
                 style="width:100%; height:400px;">

                <!-- Left Side -->
                <div class="rounded-3 card-box p-3"
                     style="width:60%; height:100%;">

                    <canvas id="myChart"></canvas>

                </div>

                <!-- Right Side -->
                <div style="width:38%; height:100%;">

                    <!-- Pending -->
                    <div class="rounded-3 card-box d-flex flex-column align-items-center justify-content-center"
                         style="height:47%;">

                        <h5>Total Pending</h5>
                        <h1 class="fw-bold text-warning">25</h1>

                    </div>

                    <!-- Transaction -->
                    <div class="rounded-3 card-box d-flex flex-column align-items-center justify-content-center mt-3"
                         style="height:47%;">

                        <h5>Total Transaction</h5>
                        <h1 class="fw-bold text-success">150</h1>

                    </div>

                </div>

            </div>

        </div>

        <script>
            $(document).ready(function () {

                // wait a little before rendering
                setTimeout(function () {

                    Chart.register(ChartDataLabels);

                    const ctx = document.getElementById('myChart');

                    new Chart(ctx, {
                        type: 'pie',

                        data: {
                            labels: ['Settlement', 'Pending'],
                            datasets: [{
                                    data: [71, 29],
                                    backgroundColor: [
                                        '#4CAF8A',
                                        '#FFB74D'
                                    ],
                                    borderColor: '#fff',
                                    borderWidth: 2
                                }]
                        },

                        options: {
                            responsive: true,
                            maintainAspectRatio: false,

                            plugins: {
                                title: {
                                    display: true,
                                    text: 'Settlement Overview',
                                    font: {
                                        size: 20
                                    }
                                },

                                legend: {
                                    position: 'bottom'
                                },

                                datalabels: {
                                    color: '#fff',
                                    font: {
                                        size: 16,
                                        weight: 'bold'
                                    },
                                    formatter: function (value) {
                                        return value + '%';
                                    }
                                }
                            }
                        }
                    });

                }, 300);

            });
        </script>
    </body>
</html>