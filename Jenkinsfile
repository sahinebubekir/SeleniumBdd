pipeline {
    agent any

    environment {
        SELENIUM_URL = 'http://localhost:4444/wd/hub'
        MAVEN_IMAGE = 'maven:3.8.7-openjdk-17'
    }

    stages {
        stage('Start Selenium Standalone Chrome') {
            steps {
                script {
                    sh '''
                        docker rm -f selenium-chrome || true
                        docker run -d --name selenium-chrome -p 4444:4444 selenium/standalone-chrome:latest
                    '''
                    sleep 10
                }
            }
        }

        stage('Checkout Code') {
            steps {
                checkout scm
            }
        }

        stage('Run Maven Tests') {
            steps {
                script {
                    docker.image(MAVEN_IMAGE).inside('--shm-size=2g') {
                        sh "mvn clean verify -Dcucumber.filter.tags=@chrome -Dselenium.url=${SELENIUM_URL}"
                    }
                }
            }
        }

        stage('Stop Selenium Container') {
            steps {
                script {
                    sh 'docker rm -f selenium-chrome || true'
                }
            }
        }
    }

    post {
        always {
            echo 'Cleaning up...'
            sh 'docker rm -f selenium-chrome || true'
        }
    }
}