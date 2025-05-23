pipeline {
    agent {
        docker {
            image 'selenium/standalone-chrome:latest'
            args '--shm-size=2g' // shared memory arttır, Chrome için önemli
        }
    }

    environment {
        MAVEN_OPTS = "-Dmaven.repo.local=.m2/repository"
        REPORT_DIR = "target/cucumber-report-html"
        CI = "true" // Bu env değişkeni DriverFactory’de CI modunu tetikler
    }

    options {
        timestamps()
        buildDiscarder(logRotator(numToKeepStr: '5'))
    }

    parameters {
        string(name: 'CUCUMBER_TAG', defaultValue: '@wip', description: 'Run tests with this Cucumber tag')
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Run Tests') {
            steps {
                sh "mvn clean verify -Dcucumber.filter.tags=${params.CUCUMBER_TAG}"
            }
        }

        stage('Publish Report') {
            steps {
                publishHTML(target: [
                    reportName : 'Cucumber Report',
                    reportDir  : "${env.REPORT_DIR}",
                    reportFiles: 'index.html'
                ])
            }
        }

        stage('Send Email') {
            steps {
                mail to: 'sahinebubekir@yahoo.com',
                     subject: "Jenkins: ${env.JOB_NAME} #${env.BUILD_NUMBER} - ${currentBuild.currentResult}",
                     body: "See report: ${env.BUILD_URL}"
            }
        }
    }
}