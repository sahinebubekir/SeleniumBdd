pipeline {
	agent any

    environment {
		SELENIUM_URL = 'http://selenium-chrome:4444/wd/hub'
        MAVEN_IMAGE = 'maven:3.9.2-eclipse-temurin-17'
    }

    stages {
		stage('Start Selenium Standalone Chrome') {
			steps {
				script {
					sh '''
                        docker rm -f selenium-chrome || true
                        docker network create selenium-net || true
                        docker run -d --name selenium-chrome --network selenium-net -p 4444:4444 selenium/standalone-chrome:latest
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
					sh """
                        docker run --rm \
                            --shm-size=2g \
                            -v ${env.WORKSPACE}:/app \
                            -w /app \
                            --network selenium-net \
                            ${MAVEN_IMAGE} \
                            mvn clean verify -Dcucumber.filter.tags=@chrome -Dselenium.url=${SELENIUM_URL}
                    """
                }
            }
        }

        stage('Stop Selenium Container') {
			steps {
				script {
					sh 'docker rm -f selenium-chrome || true'
                    sh 'docker network rm selenium-net || true'
                }
            }
        }
    }

    post {
		always {
			echo 'Cleaning up...'
            sh 'docker rm -f selenium-chrome || true'
            sh 'docker network rm selenium-net || true'
        }

        failure {
			echo 'The Pipeline failed :('
        }

        success {
			echo 'The Pipeline completed successfully :)'
        }
    }
}
