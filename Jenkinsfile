pipeline {
    agent {
        dockerfile {
            filename 'Dockerfile'
            dir '.'
        }
    }

    environment {
        MAVEN_OPTS = "-Dmaven.repo.local=.m2/repository"
        REPORT_DIR = "target/cucumber-report-html"
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

        /*stage('Send Slack Notification') {
            steps {
                slackSend(
                    channel: '#automation-notifications',
                    message: "*${env.JOB_NAME}* - Build #${env.BUILD_NUMBER} - *${currentBuild.currentResult}* (<${env.BUILD_URL}|Open>)"
                )
            }
        }*/

        stage('Send Email') {
            steps {
                mail to: 'sahinebubekir@yahoo.com',
                     subject: "Jenkins: ${env.JOB_NAME} #${env.BUILD_NUMBER} - ${currentBuild.currentResult}",
                     body: "See report: ${env.BUILD_URL}"
            }
        }
    }

    /*post {
        failure {
            slackSend(
                color: '#FF0000',
                message: "❌ Build Failed: ${env.JOB_NAME} #${env.BUILD_NUMBER} <${env.BUILD_URL}|Open>"
            )
        }
        success {
            slackSend(
                color: '#00FF00',
                message: "✅ Build Success: ${env.JOB_NAME} #${env.BUILD_NUMBER} <${env.BUILD_URL}|Open>"
            )
        }
    } */
}