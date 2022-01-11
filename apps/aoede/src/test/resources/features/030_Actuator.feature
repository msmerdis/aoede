@Swagger
Feature: Verify actuator endpoints are up and running
### Verify the setup of actuator

@TC030001
@Positive
Scenario: access actuator
### verify actuator is present in the aoede application

When request "/actuator" from aoede
Then the aoede response has a status code of 200

@TC030002
@Positive
Scenario: access actuator health check
### verify health check is present in the aoede application

When request "/actuator/health" from aoede
Then the aoede response has a status code of 200
And the aoede response matches
	| status | UP |

@TC030003
@Positive
Scenario: access actuator metrics
### verify metrics are present in the aoede application

When request "/actuator/metrics" from aoede
Then the aoede response has a status code of 200
And the aoede response contains "hikaricp.connections"
And the aoede response contains "hikaricp.connections.acquire"
And the aoede response contains "hikaricp.connections.active"
And the aoede response contains "hikaricp.connections.creation"
And the aoede response contains "hikaricp.connections.idle"
And the aoede response contains "hikaricp.connections.max"
And the aoede response contains "hikaricp.connections.min"
And the aoede response contains "hikaricp.connections.pending"
And the aoede response contains "hikaricp.connections.timeout"
And the aoede response contains "hikaricp.connections.usage"
And the aoede response contains "http.server.requests"
And the aoede response contains "jdbc.connections.active"
And the aoede response contains "jdbc.connections.idle"
And the aoede response contains "jdbc.connections.max"
And the aoede response contains "jdbc.connections.min"
And the aoede response contains "jvm.buffer.count"
And the aoede response contains "jvm.buffer.memory.used"
And the aoede response contains "jvm.buffer.total.capacity"
And the aoede response contains "jvm.classes.loaded"
And the aoede response contains "jvm.classes.unloaded"
And the aoede response contains "jvm.gc.live.data.size"
And the aoede response contains "jvm.gc.max.data.size"
And the aoede response contains "jvm.gc.memory.allocated"
And the aoede response contains "jvm.gc.memory.promoted"
And the aoede response contains "jvm.gc.pause"
And the aoede response contains "jvm.memory.committed"
And the aoede response contains "jvm.memory.max"
And the aoede response contains "jvm.memory.used"
And the aoede response contains "jvm.threads.daemon"
And the aoede response contains "jvm.threads.live"
And the aoede response contains "jvm.threads.peak"
And the aoede response contains "jvm.threads.states"
And the aoede response contains "log4j2.events"
And the aoede response contains "process.cpu.usage"
And the aoede response contains "process.files.max"
And the aoede response contains "process.files.open"
And the aoede response contains "process.start.time"
And the aoede response contains "process.uptime"
And the aoede response contains "system.cpu.count"
And the aoede response contains "system.cpu.usage"
And the aoede response contains "system.load.average.1m"
And the aoede response contains "tomcat.sessions.active.current"
And the aoede response contains "tomcat.sessions.active.max"
And the aoede response contains "tomcat.sessions.alive.max"
And the aoede response contains "tomcat.sessions.created"
And the aoede response contains "tomcat.sessions.expired"
And the aoede response contains "tomcat.sessions.rejected"

@TC030004
@Positive
Scenario: access actuator prometheus metrics
### verify prometheus metrics are present in the aoede application

When request "/actuator/prometheus" from aoede as plain text
Then the aoede response has a status code of 200
And the aoede response contains "hikaricp_connections"
And the aoede response contains "hikaricp_connections_acquire_seconds_count"
And the aoede response contains "hikaricp_connections_acquire_seconds_max"
And the aoede response contains "hikaricp_connections_acquire_seconds_sum"
And the aoede response contains "hikaricp_connections_active"
And the aoede response contains "hikaricp_connections_creation_seconds_count"
And the aoede response contains "hikaricp_connections_creation_seconds_max"
And the aoede response contains "hikaricp_connections_creation_seconds_sum"
And the aoede response contains "hikaricp_connections_idle"
And the aoede response contains "hikaricp_connections_max"
And the aoede response contains "hikaricp_connections_min"
And the aoede response contains "hikaricp_connections_pending"
And the aoede response contains "hikaricp_connections_timeout_total"
And the aoede response contains "hikaricp_connections_usage_seconds_count"
And the aoede response contains "hikaricp_connections_usage_seconds_max"
And the aoede response contains "hikaricp_connections_usage_seconds_sum"
And the aoede response contains "http_server_requests_seconds_count"
And the aoede response contains "http_server_requests_seconds_max"
And the aoede response contains "http_server_requests_seconds_sum"
And the aoede response contains "jdbc_connections_active"
And the aoede response contains "jdbc_connections_idle"
And the aoede response contains "jdbc_connections_max"
And the aoede response contains "jdbc_connections_min"
And the aoede response contains "jvm_buffer_count_buffers"
And the aoede response contains "jvm_buffer_memory_used_bytes"
And the aoede response contains "jvm_buffer_total_capacity_bytes"
And the aoede response contains "jvm_classes_loaded_classes"
And the aoede response contains "jvm_classes_unloaded_classes_total"
And the aoede response contains "jvm_gc_live_data_size_bytes"
And the aoede response contains "jvm_gc_max_data_size_bytes"
And the aoede response contains "jvm_gc_memory_allocated_bytes_total"
And the aoede response contains "jvm_gc_memory_promoted_bytes_total"
And the aoede response contains "jvm_gc_pause_seconds_count"
And the aoede response contains "jvm_gc_pause_seconds_max"
And the aoede response contains "jvm_gc_pause_seconds_sum"
And the aoede response contains "jvm_memory_committed_bytes"
And the aoede response contains "jvm_memory_max_bytes"
And the aoede response contains "jvm_memory_used_bytes"
And the aoede response contains "jvm_threads_daemon_threads"
And the aoede response contains "jvm_threads_live_threads"
And the aoede response contains "jvm_threads_peak_threads"
And the aoede response contains "jvm_threads_states_threads"
And the aoede response contains "log4j2_events_total"
And the aoede response contains "process_cpu_usage"
And the aoede response contains "process_files_max_files"
And the aoede response contains "process_files_open_files"
And the aoede response contains "process_start_time_seconds"
And the aoede response contains "process_uptime_seconds"
And the aoede response contains "system_cpu_count"
And the aoede response contains "system_cpu_usage"
And the aoede response contains "system_load_average_1m"
And the aoede response contains "tomcat_sessions_active_current_sessions"
And the aoede response contains "tomcat_sessions_active_max_sessions"
And the aoede response contains "tomcat_sessions_alive_max_seconds"
And the aoede response contains "tomcat_sessions_created_sessions_total"
And the aoede response contains "tomcat_sessions_expired_sessions_total"
And the aoede response contains "tomcat_sessions_rejected_sessions_total"
