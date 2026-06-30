# System Monitor CLI - Deployment Guide

A lightweight Java CLI tool for monitoring system resources on your VPS.

## Prerequisites

- Java 17+ installed
- Maven 3+ (for building from source)
- Linux/MacOS system

## Option 1: Run with Java (Recommended for VPS)

### 1. Build the JAR

```bash
# Clone or upload the project
git clone https://github.com/innaesim/system-monitor.git
cd system-monitor

# Build with Maven
mvn clean package -DskipTests
```

### 2. Run the Monitor

```bash
# Run once to see current system status
java -jar target/system-monitor.jar

# Run with continuous monitoring (create a simple script)
./monitor.sh
```

### 3. Create a Monitoring Script

Create a file called `monitor.sh`:

```bash
#!/bin/bash
# System Monitor Script

echo "Starting System Monitor..."
echo "Press Ctrl+C to stop"
echo ""

while true; do
    clear
    echo "=== System Status at $(date) ==="
    echo ""
    java -jar target/system-monitor.jar
    echo ""
    echo "Refreshing in 5 seconds..."
    sleep 5
done
```

Make it executable:
```bash
chmod +x monitor.sh
./monitor.sh
```

## Option 2: Run with Docker

### 1. Build Docker Image

```bash
docker build -t system-monitor .
```

### 2. Run Container

```bash
# Run once
docker run --rm system-monitor

# Run with host system access (for accurate metrics)
docker run --rm --privileged \
  -v /proc:/host/proc:ro \
  -v /sys:/host/sys:ro \
  system-monitor
```

## Option 3: Deploy as Systemd Service

For continuous background monitoring on your VPS:

### 1. Create Service File

Create `/etc/systemd/system/system-monitor.service`:

```ini
[Unit]
Description=System Monitor Service
After=network.target

[Service]
Type=simple
User=your-username
WorkingDirectory=/path/to/system-monitor
ExecStart=/usr/bin/java -jar /path/to/system-monitor/target/system-monitor.jar
Restart=on-failure
RestartSec=10

# Log output
StandardOutput=append:/var/log/system-monitor.log
StandardError=append:/var/log/system-monitor-error.log

[Install]
WantedBy=multi-user.target
```

### 2. Enable and Start Service

```bash
# Reload systemd
sudo systemctl daemon-reload

# Enable service to start on boot
sudo systemctl enable system-monitor

# Start service
sudo systemctl start system-monitor

# Check status
sudo systemctl status system-monitor

# View logs
sudo journalctl -u system-monitor -f
```

### 3. Manage Service

```bash
# Stop service
sudo systemctl stop system-monitor

# Restart service
sudo systemctl restart system-monitor

# Disable service
sudo systemctl disable system-monitor
```

## Option 4: Scheduled Monitoring with Cron

For periodic system snapshots:

### 1. Create Monitor Script

Create `/usr/local/bin/system-monitor.sh`:

```bash
#!/bin/bash
LOG_FILE="/var/log/system-monitor.log"
cd /path/to/system-monitor

echo "=== System Status at $(date) ===" >> $LOG_FILE
java -jar target/system-monitor.jar >> $LOG_FILE 2>&1
echo "" >> $LOG_FILE
```

Make it executable:
```bash
sudo chmod +x /usr/local/bin/system-monitor.sh
```

### 2. Add Cron Job

```bash
# Edit crontab
crontab -e

# Add one of these lines:

# Run every 5 minutes
*/5 * * * * /usr/local/bin/system-monitor.sh

# Run every hour
0 * * * * /usr/local/bin/system-monitor.sh

# Run daily at midnight
0 0 * * * /usr/local/bin/system-monitor.sh
```

### 3. View Logs

```bash
# View recent logs
tail -f /var/log/system-monitor.log

# View with filtering
grep "CPU" /var/log/system-monitor.log
```

## Usage Examples

### Basic Usage
```bash
java -jar target/system-monitor.jar
```

### Output to File
```bash
java -jar target/system-monitor.jar > system-status.txt
```

### Append to Log
```bash
java -jar target/system-monitor.jar >> /var/log/system-status.log
```

### Email Alerts (with mail command)
```bash
#!/bin/bash
OUTPUT=$(java -jar target/system-monitor.jar)
echo "$OUTPUT" | mail -s "VPS System Status" your-email@domain.com
```

## Integration with the API

You can use both the CLI tool and API together:
- **CLI tool**: For quick checks and scheduled reports
- **API**: For real-time monitoring via web dashboard

## Monitoring Best Practices

1. **Regular checks**: Run every 5-15 minutes to catch issues early
2. **Log rotation**: Use logrotate to manage log file sizes
3. **Alerting**: Set up email alerts when certain thresholds are exceeded
4. **Baseline**: Monitor normal system behavior to identify anomalies
5. **History**: Keep logs for at least 30 days for trend analysis

## Example Logrotate Configuration

Create `/etc/logrotate.d/system-monitor`:

```
/var/log/system-monitor.log {
    daily
    rotate 30
    compress
    delaycompress
    missingok
    notifempty
    create 0644 root root
}
```

## Troubleshooting

### Java not found
```bash
# Install Java 17+
sudo apt update
sudo apt install openjdk-17-jre-headless -y
```

### Permission denied
```bash
# Make sure the jar file is readable
chmod 644 target/system-monitor.jar

# If running as service, check user permissions
sudo chown your-username:your-username target/system-monitor.jar
```

### Missing metrics
The tool uses OSHI library which requires access to system files:
```bash
# Ensure /proc and /sys are accessible
ls -la /proc/cpuinfo
ls -la /sys/class/thermal/
```

## Uninstall

### Remove Systemd Service
```bash
sudo systemctl stop system-monitor
sudo systemctl disable system-monitor
sudo rm /etc/systemd/system/system-monitor.service
sudo systemctl daemon-reload
```

### Remove Cron Job
```bash
crontab -e
# Remove the system-monitor line and save
```

### Remove Files
```bash
rm -rf /path/to/system-monitor
sudo rm /usr/local/bin/system-monitor.sh
sudo rm /var/log/system-monitor*.log
```

## Support

For issues or questions, refer to the main README.md or create an issue on GitHub.
