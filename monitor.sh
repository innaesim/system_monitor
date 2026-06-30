#!/bin/bash
# System Monitor Script - Continuous monitoring

echo "Starting System Monitor..."
echo "Press Ctrl+C to stop"
echo ""

# Check if jar exists
if [ ! -f "target/system-monitor.jar" ]; then
    echo "Error: target/system-monitor.jar not found!"
    echo "Please run: mvn clean package"
    exit 1
fi

while true; do
    clear
    echo "=== System Status at $(date) ==="
    echo ""
    java -jar target/system-monitor.jar
    echo ""
    echo "Refreshing in 5 seconds..."
    sleep 5
done
