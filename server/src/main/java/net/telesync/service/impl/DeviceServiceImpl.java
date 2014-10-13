package net.telesync.service.impl;

import net.telesync.model.DeviceInfo;
import net.telesync.model.RegisterDeviceRequest;
import net.telesync.service.DeviceService;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class DeviceServiceImpl implements DeviceService {

    private static final class DeviceInfoRowMapper implements RowMapper<DeviceInfo> {

        @Override
        public DeviceInfo mapRow(ResultSet resultSet, int i) throws SQLException {
            Integer id = (Integer) resultSet.getObject("id");
            String url = resultSet.getString("url");
            String deviceName = resultSet.getString("device_name");
            String tvName = resultSet.getString("tv_name");
            LocalDateTime lastHeartbeat = new LocalDateTime(resultSet.getObject("last_heartbeat"));

            return new DeviceInfo(id, url, deviceName, tvName, lastHeartbeat);
        }
    }

    private static final String INSERT_SQL = "insert into devices (device_name, tv_name, last_heartbeat) " +
            " values (?, ?, ?)";

    private static final String SELECT_BY_ID_SQL = "select * from devices where id = ?";

    private static final String SELECT_ALL_SQL = "select * from devices";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DeviceServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public DeviceInfo registerNewDevice(final RegisterDeviceRequest request) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(INSERT_SQL);
                ps.setString(1, request.getDeviceName());
                ps.setString(2, request.getTvName());
                ps.setTimestamp(3, new Timestamp(DateTime.now().getMillis()));

                return ps;
            }
        }, keyHolder);

        return jdbcTemplate.queryForObject(SELECT_BY_ID_SQL,
                new Object[] { keyHolder.getKey() },
                new DeviceInfoRowMapper());
    }

    @Override
    public List<DeviceInfo> getAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, new DeviceInfoRowMapper());
    }
}
