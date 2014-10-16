package net.telesync.service.impl;

import net.telesync.exception.InvalidUpdateException;
import net.telesync.model.DeviceInfo;
import net.telesync.model.RegisterDeviceRequest;
import net.telesync.service.DeviceService;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
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

    private static final String INSERT_SQL = "insert into devices (device_name, tv_name, last_heartbeat) " +
            " values (?, ?, ?)";
    private static final String SELECT_BY_ID_SQL = "select * from devices where id = ?";
    private static final String SELECT_ALL_SQL = "select * from devices";
    private static final String MAKE_HEARTBEAT_SQL = "update devices set last_heartbeat = ? where id = ?";
    private static final String UPDATE_URL = "update devices set url = ? where id = ?";
    private static final String DELETE_DEVICE = "delete from devices where id = ?";
    private static final String GET_OUTDATED_DEVICES = "select * from devices where (? - last_heartbeat) > ? * 1000 ";

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
                ps.setTimestamp(3, new Timestamp(DateTime.now(DateTimeZone.UTC).getMillis()));

                return ps;
            }
        }, keyHolder);

        return jdbcTemplate.queryForObject(SELECT_BY_ID_SQL,
                new Object[]{ keyHolder.getKey() },
                new DeviceInfoRowMapper());
    }

    @Override
    public List<DeviceInfo> getAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, new DeviceInfoRowMapper());
    }

    @Override
    @Transactional
    public DeviceInfo getById(Integer deviceId) {
        return jdbcTemplate.queryForObject(SELECT_BY_ID_SQL, new Object[]{ deviceId }, new DeviceInfoRowMapper());
    }

    @Override
    @Transactional
    public void makeHeartbeat(Integer deviceId) {
        int rowsUpdated = jdbcTemplate.update(MAKE_HEARTBEAT_SQL, new Timestamp(DateTime.now(DateTimeZone.UTC).getMillis()), deviceId);
        if (rowsUpdated != 1) {
            throw new InvalidUpdateException("Update failed with rowsUpdated = " + rowsUpdated);
        }
    }

    @Override
    @Transactional
    public void updateDeviceUrl(Integer deviceId, DeviceInfo deviceInfo) {
        int rowsUpdated = jdbcTemplate.update(UPDATE_URL, deviceInfo.getDeviceUrl(), deviceId);
        if (rowsUpdated != 1) {
            throw new InvalidUpdateException("Update failed with rowsUpdated = " + rowsUpdated);
        }
    }

    @Override
    @Transactional
    public void removeDevice(Integer deviceId) {
        int rowsDeleted = jdbcTemplate.update(DELETE_DEVICE, deviceId);
        if (rowsDeleted != 1) {
            throw new InvalidUpdateException("Delete failed with rowsDeleted = " + rowsDeleted);
        }
    }

    @Override
    public List<DeviceInfo> findOutdatedDevices(long seconds) {
        return jdbcTemplate.query(GET_OUTDATED_DEVICES, new Object[]{
                DateTime.now(DateTimeZone.UTC).getMillis(),
                seconds
        }, new DeviceInfoRowMapper());
    }

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
}
