function gbvideo( a,b,w,x0,y0 )

aviobj = avifile('gaborWavelet.avi');
       fig=figure;
       set(fig,'DoubleBuffer','on');
% gbvideo(50,50,0.02,75,75)
height = 150;
width = height;
z = zeros(height,width);

a2 = a^2;
b2 = b^2;
for i=1:height
    for j=1:width
        z(i,j) = g(i,j,a2,b2,x0,y0)*reS(j,x0,w);
        %sum = sum + z(i,j);
    end
end
[x,y] = meshgrid(0:width-1,0:height-1);

% title(['Gabor wavelet:', ' \alpha=',num2str(a),  ' \beta=', num2str(b), ' \omega=', num2str(w), ' x_0=', num2str(x0), ' y_0=', num2str(y0) ])
mesh(x,y,z);
view(0,90)

for i=0:1:10
    view(0,i)
    frame = getframe(gcf);
    pause(0.1)
    aviobj = addframe(aviobj,frame);

end

aviobj = close(aviobj);

function g = g(x,y,a2,b2,x0,y0)
g = exp(-pi*((x-x0)^2/a2 + (y-y0)^2/b2));    

function reS = reS(x,x0,w)
reS = cos(2*pi*w*(x0+x));
