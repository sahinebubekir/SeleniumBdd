pipeline {
	agent any

    environment {
		SELENIUM_URL = 'http://selenium-chrome:4444/wd/hub'
    }

    stages {
		stage('Checkout') {
			steps {
				checkout scm
            }
        }

        stage('Setup Selenium Grid') {
			steps {
				script {
					sh '''
                        docker rm -f selenium-chrome || true
                        docker network create test-network || true
                        docker run -d --name selenium-chrome \
                            --network test-network \
                            -p 4444:4444 \
                            -p 7900:7900 \
                            --shm-size="2g" \
                            selenium/standalone-chrome:latest
                    '''
                    sh 'sleep 10'
                }
            }
        }

    }

		stage('Run Tests') {
			steps {
				script {
					sh '''
					docker run --rm \
						--network test-network \
						-v "${WORKSPACE}":/app \
						-w /app \
						maven:3.9.2-eclipse-temurin-17 \
						mvn clean verify \
						-Drun.mode=remote \
						-Dselenium.url=http://selenium-chrome:4444/wd/hub \
						-Dbrowser=chrome
				'''
			}
		}
	}

		post {
				always {
					publishHTML(target: [
					reportDir: 'target/cucumber-reports',
					reportFiles: 'index.html',
					reportName: 'Cucumber Test Report',
					allowMissing: false,
					alwaysLinkToLastBuild: true,
					keepAll: true
				])
				cleanWs()
			}
		}
}