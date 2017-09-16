#IoT End to End - Python Client

##Installation
* Open your IDE - for this example, PyDev 
* Download the iote2e project from github
* Create a project into your IDE, this project was created in PyDev
* Run setup.py with the parms: sdist --formats-gztar
* Verify build succeeded: Gzip should be created: ./dist/awsext-1.1.tar.gz
* Copy iote2epyclient-1.0.0.tar.gz to target location (i.e. SCP to an RPi or EC2 instance)
scp /home/pete/development/gitrepo/iote2e/iote2e-pyclient/dist/iote2epyclient-1.0.0.tar.gz pete@rpi-001:iote2epyclient-1.0.0.tar.gz
scp /home/pete/development/gitrepo/iote2e/iote2e-pyclient/dist/iote2epyclient-1.0.0.tar.gz pete@rpi-002:iote2epyclient-1.0.0.tar.gz
scp /home/pete/development/gitrepo/iote2e/iote2e-pyclient/dist/iote2epyclient-1.0.0.tar.gz pete@rpi-003:iote2epyclient-1.0.0.tar.gz
* Login to each target system and `cd` to target directory
* Run install script: sudo ~/development/gitrepo/iote2e/iote2e-pyclient/scripts/install-pyclient.sh
* Verify successful installation
		* Start Python interactive and enter:
				* import iote2epyclient 
				* print iote2epyclient.version
		* Should display "1.0.0" - if not, then research the error
		* Enter `exit()` to exit the python interpreter
		
##Running Simulators
###Prereqs###
Latest iote2e pulled from Github.

###Temp to Fan
**Running on python under Docker, i.e. on iote2e-demomgr**
python -m iote2epyclient.launch.clientlauncher 'ProcessSimTempToFan' 'temp1' '/home/pete/development/gitrepo/iote2e/iote2e-schema/src/main/avro/' 'ws://hp-lt-ubuntu-1:8090/iote2e/' 'pzybrick1' 'rpi-002' '/home/pete/development/gitrepo/iote2e/iote2e-pyclient/config/client_consoleonly.conf' 'temp1'

**Running on RPi**
python -m iote2epyclient.launch.clientlauncher 'ProcessSimTempToFan' 'temp1' '/home/pete/development/gitrepo/iote2e/iote2e-schema/src/main/avro/' 'ws://hp-lt-ubuntu-1:8090/iote2e/' 'pzybrick1' 'rpi-001' '/home/pete/development/gitrepo/iote2e/iote2e-pyclient/config/client_consoleonly.conf' 'temp1'

python -m iote2epyclient.launch.clientlauncher 'ProcessTempToFan' 'temp1' '/home/pete/development/gitrepo/iote2e/iote2e-schema/src/main/avro/' 'ws://hp-lt-ubuntu-1:8090/iote2e/' 'pzybrick1' 'rpi-001' '/home/pete/development/gitrepo/iote2e/iote2e-pyclient/config/client_consoleonly.conf' 'temp1'

###Humidity to Mister
python -m iote2epyclient.launch.clientlauncher 'ProcessSimHumidityToMister' 'humidity1' '/home/pete/development/gitrepo/iote2e/iote2e-schema/src/main/avro/' 'ws://192.168.1.7:8090/iote2e/' 'pzybrick1' 'rpi_001' '/home/pete/development/gitrepo/iote2e/iote2e-pyclient/config/client_consoleonly.conf' 'humidity1'

###LedGreen
python -m iote2epyclient.launch.clientlauncher 'ProcessSimTempToFan' 'switch0' '/home/pete/development/gitrepo/iote2e/iote2e-schema/src/main/avro/' 'ws://hp-lt-ubuntu-1:8090/iote2e/' 'pzybrick1' 'rpi-999' '/home/pete/development/gitrepo/iote2e/iote2e-pyclient/config/client_consoleonly.conf' 'switch0'

python -m iote2epyclient.launch.clientlauncher 'ProcessLedGreen' 'switch0' '/home/pete/development/gitrepo/iote2e/iote2e-schema/src/main/avro/' 'ws://192.168.1.7:8090/iote2e/' 'pzybrick1' 'rpi-002' '/home/pete/development/gitrepo/iote2e/iote2e-pyclient/config/client_consoleonly.conf' 'switch0'

###PillDispenser
python -m iote2epyclient.launch.clientlauncher 'ProcessSimPillDispenser' 'pilldisp1' '/home/pete/development/gitrepo/iote2e/iote2e-schema/src/main/avro/' 'ws://hp-lt-ubuntu-1:8090/iote2e/' 'pzybrick1' 'rpi-999' '/home/pete/development/gitrepo/iote2e/iote2e-pyclient/config/client_consoleonly.conf' ''

python -m iote2epyclient.launch.clientlauncher 'ProcessPillDispenser' 'pilldisp1' '/home/pete/development/gitrepo/iote2e/iote2e-schema/src/main/avro/' 'ws://hp-lt-ubuntu-1:8090/iote2e/' 'pzybrick1' 'rpi-001' '/home/pete/development/gitrepo/iote2e/iote2e-pyclient/config/client_consoleonly.conf' ''

##RPi Installation
Install Ubuntu Mate 
- Download Ubuntu Mate: https://ubuntu-mate.org/raspberry-pi/
	Copy to microSDHC - I used Etcher on MacBook, worked great
	Plug the microSDHC into the RPi and power up
	Follow the prompts
	**CRITICAL** name each RPi distinctly, use rpi-001, rpi-002 to start, since this matches the SourceName's in rule_login_source_sensor.json
Start Ubuntu Mate
	Login for the first time
	Open terminal session
		sudo apt-get update
		sudo apt-get upgrade
			This will run for a few minutes, accept all defaults
			Note: occasionally received: E: Could note get lock /var/lib/dpkg/lock
			Did this: sudo lsof /var/lib/dpkg/lock
			Found the pid, killed the process, then re-ran the apt-get
		sudo apt-get --purge autoremove
		sudo ufw enable
		sudo ufw allow 22
		sudo systemctl enable ssh.socket
		sudo systemctl restart ssh
		sudo apt-get update
		sudo apt-install ntp
		sudo apt install ntpdate
	Optionally set the RPi IP address to a fixed address, see TODO: **Master Network Setup**
	Determine the IP address of the RPi instance
		ifconfig
			Look for "encap:Ethernet"
	SCP the RPi initialization script to the RPi instance
		Enter: scp /home/pete/development/gitrepo/iote2e/iote2e-tests/iote2e-shared/scripts/rpi-init.sh pete@rpi-003:rpi-init.sh
	SSH into the RPi and run the init script
		ssh pete@192.168.1.5
		sudo ./rpi-init.sh
	Verify Avro installed
		python
			import avro -> should not throw exception
			exit()
	Optionally install gedit
		sudo apt install gedit
	Install git
		sudo apt install git
		Create a local repo, i.e. /home/pete/development/gitrepo
		cd to that local repo, i.e. cd /home/pete/development/gitrepo
		git init
		git config --global push.default simple
		git config --global user.email "pzybrick@gmail.com"
		git config --global user.name "Pete Zybrick"
		git clone https://github.com/petezybrick/iote2e.git
		cd iote2e
		git pull https://github.com/petezybrick/iote2e.git develop
		git branch develop
		git checkout develop
	Configure NTP to set time - https://askubuntu.com/questions/254826/how-to-force-a-clock-update-using-ntp
		Set now
			sudo service ntp stop
			sudo ntpdate -s time.nist.gov
			sudo service ntp start
		Update /etc/rc.local to set at startup
		( /etc/init.d/ntp stop
		until ping -nq -c3 8.8.8.8; do
		   echo "Waiting for network..."
		done
		ntpdate -s time.nist.gov
		/etc/init.d/ntp start )&
	Install PiPlates Python Support - MOTOR and DAQC
		sudo pip install pi-plates
		sudo apt-get install python-spidev
		sudo raspi-config
			NOTE: you can enable the SPI and Camera at the same time and do only a single reboot
			3 Interfacing Options
			P3 SPI
				enable SPI
			Reboot
		Motor Reference: https://pi-plates.com/downloads/MOTORplate%20Quick%20Reference%20Guide.pdf
	Enable Camera support
		sudo apt-get install python-picamera
		sudo raspi-config
			3 Interfacing Options
			P1 Camera
				enable Camera
			Reboot
			Test the camera: raspistill -e png -w 100 -h 100 -o first.png
		picamera doc: http://picamera.readthedocs.io/en/release-1.13/
	Optionally configure SSH keys
		Run ssh-keygen on your development (client) machine - only need to do this once
		Accept default file location
		Passphrase: anything you want, i.e. iote2e
		Copy to remote RPi: ssh-copy-id -i ~/.ssh/id_rsa.pub pete@rpi-001
		Enter: ssh pete@rpi-001
			First attempt will cause password prompt - use passphrase from above
			
sudo shutdown -P now

