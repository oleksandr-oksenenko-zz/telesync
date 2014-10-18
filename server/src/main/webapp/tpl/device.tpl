<td><%= id %></td>
<td><%= deviceName %></td>
<td><%= tvName %></td>
<td class="device-url">
    <span class="show url"><%= deviceUrl ? deviceUrl : "Отсутствует"  %></span>
    <input type="text" class="form-control url-input hidden">
</td>
<td><%= _.helpers.convertJodaToJs(lastHeartbeat) %></td>
<td>
    <div class="btn-group">
        <button type="button" class="btn btn-info btn-change-url">Сменить ссылку</button>
        <button type="button" class="btn btn-success btn-save-url hidden">Сохранить ссылку</button>
        <button type="button" class="btn btn-danger btn-remove-device">Удалить устройство</button>
    </div>
</td>