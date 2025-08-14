
# System Monitoring Tool

A lightweight **Java utility** for monitoring system resources in real-time. Display CPU usage, memory statistics, disk usage, and more through a **CLI interface**.

---

## Features

* ✅ CPU usage per core and overall system load
* ✅ Memory usage (total, available, and used)
* ✅ Disk space usage for all drives
* ✅ Periodic updates for real-time monitoring
* ✅ Linux, MacOS

---

## Installation

1. **Clone the repository:**

```bash
git clone https://github.com/innaesim/system-monitor.git
cd system-monitor
```

2. **Build with Maven:**

```bash
mvn clean package
```

3. **Run the tool:**

```bash
java -jar target/system-monitor.jar
```

> ⚡ Ensure you have **Java 11+** installed.

---

## Usage

### Command-Line Interface (CLI)

Run the JAR file and follow the prompts:

```bash
Enter update interval (seconds): 5
Displaying system metrics every 5 seconds...
```

Metrics include:

* CPU load
* Memory usage
* Disk usage

---

## Example Output

```
CPU Usage: 23%
Memory: 5.6 GB / 16 GB
Disk C: 120 GB free / 256 GB
Disk D: 512 GB free / 1 TB
```

---

## License

MIT License © 2025 Duncan Johanne Kachasu
